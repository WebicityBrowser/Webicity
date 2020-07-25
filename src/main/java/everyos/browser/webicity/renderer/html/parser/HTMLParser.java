package everyos.browser.webicity.renderer.html.parser;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Stack;

import everyos.browser.webicity.renderer.html.dom.Element;
import everyos.browser.webicity.renderer.html.dom.Node;
import everyos.browser.webicity.renderer.html.dom.impl.CommentImpl;
import everyos.browser.webicity.renderer.html.dom.impl.DocumentImpl;
import everyos.browser.webicity.renderer.html.dom.impl.TextNodeImpl;

public final class HTMLParser {
	public static final String HTML_NAMESPACE = "http://www.w3.org/1999/xhtml";
	
	private InsertionState istate = InsertionState.INITIAL;
	private InsertionState ostate = null;
	private TokenizeState state = TokenizeState.DATA;
	private DocumentImpl document = new DocumentImpl();
	private Stack<Element> elements = new Stack<Element>();
	@SuppressWarnings("unused")
	private Element head;
	private boolean fostering = false;
	
	public DocumentImpl getDocument() { return document; }
	
	public void parse(Reader istream) throws IOException {
		//TODO: Chunk into 8 kibibyte sections. Preload IO on seperate thread.
		PushbackReader stream = new PushbackReader(istream, 2^13+7);
		TokenizeState returnState = null;
		Token token = null;
		StringBuilder tmp_buf = new StringBuilder();
		
		char rep_char = '\uFFFD';
		
		HashMap<TokenizeState, Integer> calls = new HashMap<>();
		
		boolean eof = false;
		int i2 = 0;
		while (!eof) {
			//System.out.println(++i2);
			int ich = stream.read();
			eof = eof|ich==-1;
			int ch = eof?'\0':ich;
			//calls.put(state, calls.getOrDefault(state, 0)+1);
			switch (state) {
				case DATA:
					if (ch=='&') {
						returnState = TokenizeState.DATA;
						state = TokenizeState.CHARACTER_REFERENCE;
					} else if (ch=='<') {
						state = TokenizeState.TAG_OPEN;
					} else if (eof) {
						emit(new EOFToken());
					} else {
						emit(new CharToken(ch));
					}
					break;
				case RCDATA:
					if (ch=='&') {
						returnState = TokenizeState.RCDATA;
						state = TokenizeState.CHARACTER_REFERENCE;
					} else if (ch=='<') {
						state = TokenizeState.RCDATA_LT_SIGN;
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						emit(new CharToken(rep_char));
					} else {
						emit(new CharToken(ch));
					}
					break;
				case RAWTEXT:
					if (ch=='<') {
						state = TokenizeState.RAWTEXT_LT_SIGN;
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						emit(new CharToken(rep_char));
					} else {
						emit(new CharToken(ch));
					}
					break;
				case SCRIPT_DATA:
					if (ch=='<') {
						state = TokenizeState.SCRIPT_DATA_LT_SIGN;
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						emit(new CharToken(rep_char));
					} else {
						emit(new CharToken(ch));
					}
					break;
				case PLAINTEXT:
					if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						emit(new CharToken(rep_char));
					} else {
						emit(new CharToken(ch));
					}
					break;
				case TAG_OPEN:
					if (ch=='!') {
						state = TokenizeState.MARKUP_DECLARATION_OPEN;
					} else if (ch=='/') {
						state = TokenizeState.END_TAG_OPEN;
					} else if (Character.isAlphabetic(ch)) {
						token = new TagToken("", false);
						stream.unread(ch);
						state = TokenizeState.TAG_NAME;
					} else if (ch=='?') {
						token = new CommentToken("");
						state = TokenizeState.BOGUS_COMMENT;
					} else if (eof) {
						emit(new CharToken('<'));
						emit(new EOFToken());
					} else {
						emit(new CharToken('<'));
						stream.unread(ch);
						state = TokenizeState.DATA;
					}
					break;
				case END_TAG_OPEN:
					if (Character.isAlphabetic(ch)) {
						token = new TagToken("", true);
						stream.unread(ch);
						state = TokenizeState.TAG_NAME;
					} else if (ch=='>') {
						state = TokenizeState.DATA;
					} else if (eof) {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						emit(new EOFToken());
					} else {
						token = new CommentToken("");
						state = TokenizeState.BOGUS_COMMENT;
					}
					break;
				case TAG_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1) {
						state = TokenizeState.BEFORE_ATTRIBUTE_NAME;
					} else if (ch=='/') {
						state = TokenizeState.SELF_CLOSING_START_TAG;
					} else if (ch=='>') {
						state = TokenizeState.DATA;
						emit(token);
					} else if (Character.isAlphabetic(ch)&&Character.isUpperCase(ch)) {
						((TagToken) token).name.appendCodePoint(ch+32);
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						((TagToken) token).name.append(rep_char);
					} else {
						((TagToken) token).name.appendCodePoint(ch);
					}
					break;
				case RCDATA_LT_SIGN:
					if (ch=='/') {
						tmp_buf = new StringBuilder();
						state = TokenizeState.RCDATA_END_TAG_OPEN;
					} else {
						emit(new CharToken('<'));
						stream.unread(ch);
						state = TokenizeState.RCDATA;
					}
					break;
				case RCDATA_END_TAG_OPEN:
					if (Character.isAlphabetic(ch)) {
						token = new TagToken("", true);
						stream.unread(ch);
						state = TokenizeState.RCDATA_END_TAG_NAME;
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						stream.unread(ch);
						state = TokenizeState.RCDATA;
					}
					break;
				case RCDATA_END_TAG_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1&&token instanceof TagToken) {
						state = TokenizeState.BEFORE_ATTRIBUTE_NAME;
					} else if (ch=='/'&&token instanceof TagToken) {
						state = TokenizeState.SELF_CLOSING_START_TAG;
					} else if (ch=='>'&&token instanceof TagToken) {
						state = TokenizeState.DATA;
					} else if (Character.isAlphabetic(ch)) {
						((TagToken) token).name.appendCodePoint(Character.toLowerCase(ch));
						tmp_buf.appendCodePoint(ch);
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						for (int i=0; i<tmp_buf.length(); i++) emit(new CharToken(tmp_buf.charAt(i)));
						stream.unread(ch);
						state = TokenizeState.RCDATA;
					}
					break;
				case RAWTEXT_LT_SIGN:
					if (ch=='/') {
						tmp_buf = new StringBuilder();
						state = TokenizeState.RAWTEXT_END_TAG_OPEN;
					} else {
						emit(new CharToken('<'));
						stream.unread(ch);
						state = TokenizeState.RAWTEXT;
					}
					break;
				case RAWTEXT_END_TAG_OPEN:
					if (Character.isAlphabetic(ch)) {
						token = new TagToken("", true);
						stream.unread(ch);
						state = TokenizeState.RAWTEXT_END_TAG_NAME;
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						stream.unread(ch);
						state = TokenizeState.RAWTEXT;
					}
					break;
				case RAWTEXT_END_TAG_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1&&token instanceof TagToken) {
						state = TokenizeState.BEFORE_ATTRIBUTE_NAME;
					} else if (ch=='/'&&token instanceof TagToken) {
						state = TokenizeState.SELF_CLOSING_START_TAG;
					} else if (ch=='>'&&token instanceof TagToken) {
						state = TokenizeState.DATA;
					} else if (Character.isAlphabetic(ch)) {
						((TagToken) token).name.appendCodePoint(Character.toLowerCase(ch));
						tmp_buf.appendCodePoint(ch);
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						for (int i=0; i<tmp_buf.length(); i++) emit(new CharToken(tmp_buf.charAt(i)));
						stream.unread(ch);
						state = TokenizeState.RAWTEXT;
					}
					break;
				case SCRIPT_DATA_LT_SIGN:
					if (ch=='/') {
						tmp_buf = new StringBuilder();
						state = TokenizeState.SCRIPT_DATA_END_TAG_OPEN;
					} else if (ch=='!') {
						state = TokenizeState.SCRIPT_DATA_ESCAPE_START;
						emit(new CharToken('<'));
						emit(new CharToken('!'));
					} else {
						emit(new CharToken('<'));
						stream.unread(ch);
						state = TokenizeState.SCRIPT_DATA;
					}
					break;
				case SCRIPT_DATA_END_TAG_OPEN:
					if (Character.isAlphabetic(ch)) {
						token = new TagToken("", true);
						stream.unread(ch);
						state = TokenizeState.SCRIPT_DATA_END_TAG_NAME;
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						stream.unread(ch);
						state = TokenizeState.SCRIPT_DATA;
					}
					break;
				case SCRIPT_DATA_END_TAG_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1&&token instanceof TagToken) {
						state = TokenizeState.BEFORE_ATTRIBUTE_NAME;
					} else if (ch=='/'&&token instanceof TagToken) {
						state = TokenizeState.SELF_CLOSING_START_TAG;
					} else if (ch=='>'&&token instanceof TagToken) {
						state = TokenizeState.DATA;
						emit(token);
						//TODO: Kind of not finished
					} else if (Character.isAlphabetic(ch)) {
						((TagToken) token).name.appendCodePoint(Character.toLowerCase(ch));
						tmp_buf.appendCodePoint(ch);
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						for (int i=0; i<tmp_buf.length(); i++) emit(new CharToken(tmp_buf.charAt(i)));
						stream.unread(ch);
						state = TokenizeState.SCRIPT_DATA;
					}
					break;
				case SCRIPT_DATA_ESCAPE_START:
					if (ch=='-') {
						state = TokenizeState.SCRIPT_DATA_ESCAPE_START_DASH;
						emit(new CharToken('-'));
					} else {
						stream.unread(ch);
						state = TokenizeState.SCRIPT_DATA;
					}
					break;
				case SCRIPT_DATA_ESCAPE_START_DASH:
					if (ch=='-') {
						state = TokenizeState.SCRIPT_DATA_ESCAPED_DASH_DASH;
						emit(new CharToken('-'));
					} else {
						stream.unread(ch);
						state = TokenizeState.SCRIPT_DATA;
					}
					break;
				case SCRIPT_DATA_ESCAPED:
					if (ch=='-') {
						state = TokenizeState.SCRIPT_DATA_ESCAPED_DASH;
						emit(new CharToken('-'));
					} else if (ch=='<') {
						state = TokenizeState.SCRIPT_DATA_ESCAPED_LT_SIGN;
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						emit(new CharToken(rep_char));
					} else {
						emit(new CharToken(ch));
					}
					break;
				case SCRIPT_DATA_ESCAPED_DASH:
					if (ch=='-') {
						state = TokenizeState.SCRIPT_DATA_ESCAPED_DASH_DASH;
						emit(new CharToken('-'));
					} else if (ch=='<') {
						state = TokenizeState.SCRIPT_DATA_ESCAPED_LT_SIGN;
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						state = TokenizeState.SCRIPT_DATA_ESCAPED;
						emit(new CharToken(rep_char));
					} else {
						state = TokenizeState.SCRIPT_DATA_ESCAPED;
						emit(new CharToken(ch));
					}
					break;
				case SCRIPT_DATA_ESCAPED_DASH_DASH:
					if (ch=='-') {
						emit(new CharToken('-'));
					} else if (ch=='<') {
						state = TokenizeState.SCRIPT_DATA_ESCAPED_LT_SIGN;
					} else if (ch=='>') {
						state = TokenizeState.SCRIPT_DATA;
						emit(new CharToken('>'));
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						state = TokenizeState.SCRIPT_DATA_ESCAPED;
						emit(new CharToken(rep_char));
					} else {
						state = TokenizeState.SCRIPT_DATA_ESCAPED;
						emit(new CharToken(ch));
					}
					break;
				case SCRIPT_DATA_ESCAPED_LT_SIGN:
					if (ch=='/') {
						tmp_buf = new StringBuilder();
						state = TokenizeState.SCRIPT_DATA_ESCAPED_END_TAG_OPEN;
					} else if (Character.isAlphabetic(ch)) {
						tmp_buf = new StringBuilder();
						emit(new CharToken('<'));
						stream.unread(ch);
						state = TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPE_START;
					} else {
						emit(new CharToken('<'));
						stream.unread(ch);
						state = TokenizeState.SCRIPT_DATA_ESCAPED;
					}
					break;
					
				case SCRIPT_DATA_ESCAPED_END_TAG_OPEN:
					if (Character.isAlphabetic(ch)) {
						token = new TagToken("", true);
						stream.unread(ch);
						state = TokenizeState.SCRIPT_DATA_ESCAPED_END_TAG_NAME;
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						stream.unread(ch);
						state = TokenizeState.SCRIPT_DATA_ESCAPED;
					}
					break;
				case SCRIPT_DATA_ESCAPED_END_TAG_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1&&token instanceof TagToken) {
						state = TokenizeState.BEFORE_ATTRIBUTE_NAME;
					} else if (ch=='/'&&token instanceof TagToken) {
						state = TokenizeState.SELF_CLOSING_START_TAG;
					} else if (ch=='>'&&token instanceof TagToken) {
						state = TokenizeState.DATA;
					} else if (Character.isAlphabetic(ch)) {
						((TagToken) token).name.append(Character.toLowerCase(ch));
						tmp_buf.appendCodePoint(ch);
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						for (int i=0; i<tmp_buf.length(); i++) emit(new CharToken(tmp_buf.charAt(i)));
						stream.unread(ch);
						state = TokenizeState.SCRIPT_DATA_ESCAPED;
					}
					break;
				case SCRIPT_DATA_DOUBLE_ESCAPE_START:
					if (("\t\n\f />").indexOf(ch)!=-1&&token instanceof TagToken) {
						state = tmp_buf.toString().equals("script")?
							TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPED:
							TokenizeState.SCRIPT_DATA_ESCAPED;
						emit(new CharToken(ch));
					} else if (Character.isAlphabetic(ch)) {
						tmp_buf.appendCodePoint(Character.toLowerCase(ch)); //TODO: Append lowercase elsewhere
						emit(new CharToken(ch));
					} else {
						stream.unread(ch);
						state = TokenizeState.SCRIPT_DATA_ESCAPED;
					}
					break;
				case SCRIPT_DATA_DOUBLE_ESCAPED:
					if (ch=='-') {
						state = TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPED_DASH;
						emit(new CharToken('-'));
					} else if (ch=='<') {
						state = TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPED_LT_SIGN;
						emit(new CharToken('<'));
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						emit(new CharToken(rep_char));
					} else {
						emit(new CharToken(ch));
					}
					break;
				case SCRIPT_DATA_DOUBLE_ESCAPED_DASH:
					if (ch=='-') {
						state = TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPED_DASH_DASH;
						emit(new CharToken('-'));
					} else if (ch=='<') {
						state = TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPED_LT_SIGN;
						emit(new CharToken('<'));
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						emit(new CharToken(rep_char));
					} else {
						state = TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPED;
						emit(new CharToken(ch));
					}
					break;
				case SCRIPT_DATA_DOUBLE_ESCAPED_DASH_DASH:
					if (ch=='-') {
						emit(new CharToken('-'));
					} else if (ch=='<') {
						state = TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPED_LT_SIGN;
					} else if (ch=='>') {
						state = TokenizeState.SCRIPT_DATA;
						emit(new CharToken('>'));
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						state = TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPED;
						emit(new CharToken(rep_char));
					} else {
						state = TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPED;
						emit(new CharToken(ch));
					}
					break;
				case SCRIPT_DATA_DOUBLE_ESCAPED_LT_SIGN:
					if (ch=='/') {
						tmp_buf = new StringBuilder();
						state = TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPE_END;
						emit(new CharToken('/'));
					} else {
						stream.unread(ch);
						state = TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPED;
					}
					break;
				case SCRIPT_DATA_DOUBLE_ESCAPE_END:
					if (("\t\n\f />").indexOf(ch)!=-1&&token instanceof TagToken) {
						state = tmp_buf.toString().equals("script")?
							TokenizeState.SCRIPT_DATA_ESCAPED:
							TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPED;
						emit(new CharToken(ch));
					} else if (Character.isAlphabetic(ch)) {
						tmp_buf.appendCodePoint(Character.toLowerCase(ch)); //TODO: Append lowercase elsewhere
						emit(new CharToken(ch));
					} else {
						stream.unread(ch);
						state = TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPED;
					}
					break;
				case BEFORE_ATTRIBUTE_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1) {
					} else if (ch=='/'||ch=='>'||eof) {
						stream.unread(ch);
						state = TokenizeState.AFTER_ATTRIBUTE_NAME;
					} else if (ch=='=') {
						((TagToken) token).attribute_name = new StringBuilder();
						((TagToken) token).attribute_name.appendCodePoint(ch);
						((TagToken) token).attribute_value = new StringBuilder();
						state = TokenizeState.ATTRIBUTE_NAME;
					} else {
						((TagToken) token).attribute_name = new StringBuilder();
						((TagToken) token).attribute_value = new StringBuilder();
						stream.unread(ch);
						state = TokenizeState.ATTRIBUTE_NAME;
					}
					break;
				case ATTRIBUTE_NAME:
					//TODO: Finalize attr
					if (("\t\n\f />").indexOf(ch)!=-1||eof) {
						stream.unread(ch);
						state = TokenizeState.AFTER_ATTRIBUTE_NAME;
					} else if (ch=='=') {
						state = TokenizeState.BEFORE_ATTRIBUTE_VALUE;
					} else if (ch=='\0') {
						((TagToken) token).attribute_name.append(rep_char);
					} else {
						((TagToken) token).attribute_name.appendCodePoint(Character.toLowerCase(ch));
					}
					break;
				case AFTER_ATTRIBUTE_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1) {
					} else if (ch=='/') {
						state = TokenizeState.SELF_CLOSING_START_TAG;
					} else if (ch=='=') {
						state = TokenizeState.BEFORE_ATTRIBUTE_VALUE;
					} else if (ch=='>') {
						state = TokenizeState.DATA;
						emit(token);
					} else if (eof) {
						emit(new EOFToken());
					} else {
						((TagToken) token).attribute_name = new StringBuilder();
						((TagToken) token).attribute_value = new StringBuilder();
						stream.unread(ch);
						state = TokenizeState.ATTRIBUTE_NAME;
					}
					break;
				case BEFORE_ATTRIBUTE_VALUE:
					if (("\t\n\f ").indexOf(ch)!=-1) {
					} else if (ch=='"') {
						state = TokenizeState.ATTRIBUTE_VALUE_DQ;
					} else if (ch=='\'') {
						state = TokenizeState.ATTRIBUTE_VALUE_SQ;
					} else if (ch=='>') {
						state = TokenizeState.DATA;
						emit(token);
					} else {
						stream.unread(ch);
						state = TokenizeState.ATTRIBUTE_VALUE_UQ;
					}
					break;
				case ATTRIBUTE_VALUE_DQ:
					if (ch=='"') {
						state = TokenizeState.AFTER_ATTRIBUTE_VALUE_QUOTED;
					} else if (ch=='&') {
						returnState = TokenizeState.ATTRIBUTE_VALUE_DQ;
						state = TokenizeState.CHARACTER_REFERENCE;
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						((TagToken) token).attribute_value.appendCodePoint(rep_char);
					} else {
						//System.out.print((char) ch);
						((TagToken) token).attribute_value.appendCodePoint(ch);
					}
					break;
				case ATTRIBUTE_VALUE_SQ:
					if (ch=='\'') {
						state = TokenizeState.AFTER_ATTRIBUTE_VALUE_QUOTED;
					} else if (ch=='&') {
						returnState = TokenizeState.ATTRIBUTE_VALUE_SQ;
						state = TokenizeState.CHARACTER_REFERENCE;
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						((TagToken) token).attribute_value.appendCodePoint(rep_char);
					} else {
						((TagToken) token).attribute_value.appendCodePoint(ch);
					}
				case ATTRIBUTE_VALUE_UQ:
					if (("\t\n\f ").indexOf(ch)!=-1) {
						state = TokenizeState.BEFORE_ATTRIBUTE_NAME;
					} else if (ch=='&') {
						returnState = TokenizeState.ATTRIBUTE_VALUE_UQ;
						state = TokenizeState.CHARACTER_REFERENCE;
					} else if (ch=='>') {
						state = TokenizeState.DATA;
						emit(token);
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						((TagToken) token).attribute_value.append(rep_char);
					} else {
						((TagToken) token).attribute_value.appendCodePoint(ch);
					}
					break;
				case AFTER_ATTRIBUTE_VALUE_QUOTED:
					if (("\t\n\f ").indexOf(ch)!=-1) {
						state = TokenizeState.BEFORE_ATTRIBUTE_NAME;
					} else if (ch=='/') {
						state = TokenizeState.SELF_CLOSING_START_TAG;
					} else if (ch=='>') {
						state = TokenizeState.DATA;
						emit(token);
					} else if (eof) {
						emit(new EOFToken());
					} else {
						stream.unread(ch);
						state = TokenizeState.BEFORE_ATTRIBUTE_NAME;
					}
					break;
				case SELF_CLOSING_START_TAG:
					if (ch=='>') {
						((TagToken) token).selfClosing = true;
						state = TokenizeState.DATA;
						emit(token);
					} else if (eof) {
						emit(new EOFToken());
					} else {
						stream.unread(ch);
						state = TokenizeState.BEFORE_ATTRIBUTE_NAME;
					}
					break;
				case BOGUS_COMMENT:
					if (ch=='>') {
						state = TokenizeState.DATA;
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						((CommentToken) token).data+=rep_char;
					} else {
						((CommentToken) token).data+=(char) ch;
					}
					break;
				case MARKUP_DECLARATION_OPEN:
					stream.unread(ch);
					if (consumeIfEquals(stream, "--")) {
						token = new CommentToken("");
						state = TokenizeState.COMMENT_START;
					} else if (consumeIfEqualsCI(stream, "DOCTYPE")) {
						state = TokenizeState.DOCTYPE;
					} else if (consumeIfEquals(stream, "[CDATA[")) {
						//TODO:
						token = new CommentToken("[CDATA[");
						state = TokenizeState.BOGUS_COMMENT;
					} else {
						token = new CommentToken("");
						state = TokenizeState.BOGUS_COMMENT;
					}
					break;
				case COMMENT_START:
					if (ch=='-') {
						state = TokenizeState.COMMENT_START_DASH;
					} else if (ch=='>') {
						state = TokenizeState.DATA;
						emit(token);
					} else {
						stream.unread(ch);
						state = TokenizeState.COMMENT;
					}
					break;
				case COMMENT_START_DASH:
					if (ch=='-') {
						state = TokenizeState.COMMENT_END;
					} else if (ch=='>') {
						state = TokenizeState.DATA;
						emit(token);
					} else if (eof) {
						emit(token);
						emit(new EOFToken());
					} else {
						((CommentToken) token).data+='-';
						stream.unread(ch);
						state = TokenizeState.COMMENT;
					}
					break;
				case COMMENT:
					if (ch=='<') {
						((CommentToken) token).data+=(char) ch;
						state = TokenizeState.COMMENT_LT_SIGN;
					} else if (ch=='-') {
						state = TokenizeState.COMMENT_END_DASH;
					} else if (eof) {
						emit(token);
						emit(new EOFToken());
					} else if (ch=='\0') {
						((CommentToken) token).data+=rep_char;
					} else {
						((CommentToken) token).data+=(char) ch;
					}
					break;
				case COMMENT_LT_SIGN:
					if (ch=='!') {
						((CommentToken) token).data+=(char) ch;
						state = TokenizeState.COMMENT_LT_SIGN_BANG;
					} else if (ch=='<') {
						((CommentToken) token).data+=(char) ch;
					} else {
						stream.unread(ch);
						state = TokenizeState.DATA;
					}
					break;
				case COMMENT_LT_SIGN_BANG:
					if (ch=='-') {
						state = TokenizeState.COMMENT_LT_SIGN_BANG_DASH;
					} else {
						stream.unread(ch);
						state = TokenizeState.COMMENT;
					}
					break;
				case COMMENT_LT_SIGN_BANG_DASH:
					if (ch=='-') {
						state = TokenizeState.COMMENT_LT_SIGN_BANG_DASH_DASH;
					} else {
						stream.unread(ch);
						state = TokenizeState.COMMENT_END_DASH;
					}
					break;
				case COMMENT_LT_SIGN_BANG_DASH_DASH:
					stream.unread(ch);
					state = TokenizeState.COMMENT_END;
					break;
				case COMMENT_END_DASH:
					if (ch=='-') {
						state = TokenizeState.COMMENT_END;
					} else if (eof) {
						emit(token);
						emit(new EOFToken());
					} else {
						((CommentToken) token).data+='-';
						stream.unread(ch);
						state = TokenizeState.COMMENT;
					}
					break;
				case COMMENT_END:
					if (ch=='>') {
						state = TokenizeState.DATA;
						emit(token);
					} else if (ch=='!') {
						state = TokenizeState.COMMENT_END_BANG;
					} else if (ch=='-') {
						((CommentToken) token).data+='-';
					} else if (eof) {
						emit(token);
						emit(new EOFToken());
					} else {
						emit(new CharToken('-'));
						emit(new CharToken('-'));
						stream.unread(ch);
						state = TokenizeState.COMMENT;
					}
					break;
				case COMMENT_END_BANG:
					if (ch=='-') {
						emit(new CharToken('-'));
						emit(new CharToken('-'));
						emit(new CharToken('!'));
						state = TokenizeState.COMMENT_END_DASH;
					} else if (ch=='>') {
						state = TokenizeState.DATA;
						emit(token);
					} else if (eof) {
						emit(token);
						emit(new EOFToken());
					} else {
						emit(new CharToken('-'));
						emit(new CharToken('-'));
						emit(new CharToken('!'));
						stream.unread(ch);
						state = TokenizeState.COMMENT;
					}
					break;
				case DOCTYPE:
					if (("\t\n\f ").indexOf(ch)!=-1) {
						state = TokenizeState.BEFORE_DOCTYPE_NAME;
					} else if (ch=='>') {
						stream.unread(ch);
						state = TokenizeState.BEFORE_DOCTYPE_NAME;
					} else if (eof) {
						DoctypeToken toke = new DoctypeToken();
						toke.forceQuirks = true;
						emit(toke);
						emit(new EOFToken());
					} else {
						stream.unread(ch);
						state = TokenizeState.BEFORE_DOCTYPE_NAME;
					}
					break;
				case BEFORE_DOCTYPE_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1) {
					} else if (eof) {
						DoctypeToken toke = new DoctypeToken();
						toke.forceQuirks = true;
						emit(toke);
						emit(new EOFToken());
					} else if (ch=='\0') {
						DoctypeToken toke = new DoctypeToken();
						toke.name = String.valueOf(rep_char);
						token = toke;
						state = TokenizeState.DATA;
					} else if (ch=='>') {
						DoctypeToken toke = new DoctypeToken();
						toke.forceQuirks = true;
						state = TokenizeState.DATA;
						emit(toke);
					} else {
						DoctypeToken toke = new DoctypeToken();
						toke.name = String.valueOf(ch).toLowerCase();
						token = toke;
						state = TokenizeState.DOCTYPE_NAME;
					}
					break;
					
				case DOCTYPE_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1) {
						state = TokenizeState.AFTER_DOCTYPE_NAME;
					} else if (ch=='>') {
						state = TokenizeState.DATA;
					} else if (eof) {
						((DoctypeToken) token).forceQuirks = true;
						emit(token);
						emit(new EOFToken());
					} else {
						((DoctypeToken) token).name+=ch=='\0'?rep_char:(char) ch;
					}
					break;
					
				//TODO: 56 onwards
					
				case CHARACTER_REFERENCE:
					stream.unread(ch);
					state = returnState;
					break;
					
				default:
					break;
			}
		}
	}

	/*private String peek(PushbackInputStream stream, int i) {
		char[] b = new char[i];
		try {
			stream.read(b, 0, i);
			stream.unread(b);
		} catch (IOException e) {
			return "";
		}
		return new String(b);
	}*/
	
	private boolean consumeIfEquals(PushbackReader stream, String s) {
		char[] b = new char[s.length()];
		try {
			stream.read(b, 0, s.length());
			if (new String(b).equals(s)) return true;
			stream.unread(b);
		} catch (IOException e) {e.printStackTrace();}
		return false;
	}
	private boolean consumeIfEqualsCI(PushbackReader stream, String s) {
		char[] b = new char[s.length()];
		try {
			stream.read(b, 0, s.length());
			if (new String(b).toLowerCase().equals(s.toLowerCase())) return true;
			stream.unread(b);
		} catch (IOException e) {e.printStackTrace();}
		return false;
	}

	private void emit(final InsertionState cstate, final Token token) {
		String name = null;
		if (token instanceof TagToken) {
			name = ((TagToken) token).name.toString();
			//System.out.println(cstate);
			//System.out.println(name+":"+(((TagToken) token).isEnd?"END":"START"));
		}
		switch(cstate) {
			case INITIAL:
				if (isWhitespace(token)) {	
				} else if (token instanceof CommentToken) {
					insertComment((CommentToken) token, document);
				} else if (token instanceof DoctypeToken) {
					//TODO:
				} else {
					//TODO:
					istate = InsertionState.BEFORE_HTML;
					emit(token);
				}
				break;
			case BEFORE_HTML:
				if (token instanceof DoctypeToken) {
				}  else if (token instanceof CommentToken) {
					insertComment((CommentToken) token, document);
				} else if (isWhitespace(token)) {
				} else if (isStartTag(token) && name.equals("html")) {
					Element el = createElement((TagToken) token, HTML_NAMESPACE, document);
					document.appendChild(el);
					elements.push(el);
					//TODO:
					istate = InsertionState.BEFORE_HEAD;
				} else if (isEndTag(token) && !tagIs(name, "head", "body", "html", "br")) {
				} else {
					//TODO
					Element el = createElement_1("html", document);
					document.appendChild(el);
					elements.push(el);
					istate = InsertionState.BEFORE_HEAD;
				}
				break;
			case BEFORE_HEAD:
				if (isWhitespace(token)) {
				} else if (token instanceof CommentToken) {
					//TODO
				} else if (token instanceof DoctypeToken) {
				} else if (isStartTag(token)&&name.equals("html")) {
					emit(InsertionState.IN_BODY, token);
				} else if (isStartTag(token)&&name.equals("head")) {
					//TODO:
					head = insertElement((TagToken) token, HTML_NAMESPACE);
					istate = InsertionState.IN_HEAD;
				} else if (isEndTag(token) && !tagIs(name, "head", "body", "html", "br")) {
				} else {
					//TODO:
					istate = InsertionState.IN_HEAD;
					emit(token);
				}
				break;
			case IN_HEAD:
				if (isWhitespace(token)) {
					//TODO
				} else if (token instanceof CommentToken) {
					//TODO
				} else if (token instanceof DoctypeToken) {
				} else if (isStartTag(token) && name.equals("html")) {
					emit(InsertionState.IN_BODY, token);
				} else if (isStartTag(token)&&tagIs(name, "base", "basefont", "bgsound", "link")) {
					//TODO:
				} else if (isStartTag(token) && name.equals("meta")) {
					insertElement((TagToken) token, HTML_NAMESPACE);
					elements.pop();
					//TODO
				} else if (isStartTag(token) && name.equals("title")) {
					//TODO:
				} else if (isStartTag(token) && tagIs(name, "noframes", "style")) {
					//TODO: Handle noscript
					//TODO:
					insertElement((TagToken) token, HTML_NAMESPACE);
					state = TokenizeState.RAWTEXT;
					ostate = istate;
					istate = InsertionState.TEXT;
				} else if (isStartTag(token) && name.equals("script")) {
					//System.out.println("\n***SCRIPT ENTER***");
					//TODO:
					Node insertion = getNodeInsertionLocation(null);
					createElement((TagToken) token, HTML_NAMESPACE, insertion);
					state = TokenizeState.SCRIPT_DATA;
					ostate = istate;
					istate = InsertionState.TEXT;
				} else if (isEndTag(token) && name.equals("head")) {
					elements.pop();
					istate = InsertionState.AFTER_HEAD;
				} else if (isEndTag(token) && !tagIs(name,  "body", "html", "br")) { /*TODO: Template*/ 
				} else if (isStartTag(token) && name.equals("head") || isEndTag(token)) {
				} else {
					//System.out.println(name);
					if (!elements.isEmpty()) elements.pop();
					istate = InsertionState.AFTER_HEAD;
					emit(token);
				}
				break;
				
			//TODO: In head noscript
				
			//TODO: Actually care
			case AFTER_HEAD:
				if (token instanceof CharToken && ("\t\n\f\r ").indexOf(((CharToken) token).ch)!=-1) {
					insertCharacter(((CharToken) token).ch);
				} else if (isStartTag(token) && name.equals("body")) {
					Element e = insertElement((TagToken) token, HTML_NAMESPACE);
					System.out.println(e+" parent is "+e.getParentNode());
					//TODO
					istate = InsertionState.IN_BODY;
				} else {
					//System.out.println("T:"+token.toString());
					//if (token instanceof CharToken ) System.out.println((int) ((CharToken) token).ch);
					Element e = insertElement(new TagToken("body", false), HTML_NAMESPACE);
					//TODO: Why is this being fired?
					istate = InsertionState.IN_BODY;
					emit(token);
				}
				break;
				
			case IN_BODY:
				if (token instanceof CharToken) {
					//TODO: All char processing
					char data = ((CharToken) token).ch;
					if (data=='\0') break;
					//System.out.print(data);
					insertCharacter(data);
				//TODO: Many things in between
				} else if ((isStartTag(token) && tagIs(name, "base", "basefont", "bgsound", "link", 
						"meta", "noframes", "script", "style", /*"template",*/ "title"))
						/*|| (isEndTag(token) && name.equals("template"))*/) {
					emit(InsertionState.IN_HEAD, token);
				//TODO: Many things in between
				} else if (isStartTag(token) && tagIs(name, "address", "article", "aside", "blockquote", "center", "details",
						"dialog", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "main", "menu",
						"nav", "ol", "p", "section", "summary", "ul")) {
					
					//TODO: Button in scope
					insertElement((TagToken) token, HTML_NAMESPACE);
					//System.out.println(elements.size());
					//TODO: Many things in between
				} else if (isEndTag(token) && tagIs(name, "address", "article", "aside", "blockquote", "center", "details",
						"dialog", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "main", "menu",
						"nav", "ol", "p", "section", "summary", "ul")) {
					
					
					//TODO: Not at all spec compliant
					//System.out.println(name);
					Stack<Element> s = new Stack<>();
					while (!elements.isEmpty()) {
						//System.out.println(elements.size());
						s.add(elements.pop());
						if (s.peek().getTagName().equals(name)) break;
						if (elements.isEmpty()) {
							while(!s.isEmpty()) elements.push(s.pop());
							break;
						}
					}
					//while(!elements.pop().tagName.equals(name)) {}
					//System.out.println("B");
					
					//TODO: Many things in between
				} else if (isStartTag(token) && name.equals("a")) {
					//TODO: Active elements
					insertElement((TagToken) token, HTML_NAMESPACE);
					
					//TODO: Many things in between
				} else if (isEndTag(token) && name.equals("a")) {
					//TODO: algorithim
					
					Stack<Element> s = new Stack<>();
					while (!elements.isEmpty()) {
						s.add(elements.pop());
						if (s.peek().getTagName().equals(name)) break;
						if (elements.isEmpty()) {
							while(!s.isEmpty()) elements.push(s.pop());
							break;
						}
					}
				}
				break;
				
			case TEXT:
				//TODO:
				if (token instanceof CharToken) {
					//insertCharacter(((CharToken) token).ch);
					//System.out.print(((CharToken) token).ch);
				} else if (isEndTag(token) && name.equals("script")) {
					//System.out.println("\n***SCRIPT EXIT***");
					Element e = elements.isEmpty()?null:elements.pop();
					istate = ostate;
				}
				break;
		}
	}

	private void emit(Token token) {
		emit(istate, token);
	}
	
	private boolean tagIs(String tname, String... names) {
		for (String name: names) {
			if (tname.equals(name)) return true;
		}
		return false;
	}
	private boolean isWhitespace(Token token) {
		return token instanceof CharToken && ("\t\n\f\r ").indexOf(((CharToken) token).ch)!=-1;
	}
	private boolean isStartTag(Token toke) {
		return toke instanceof TagToken && !((TagToken) toke).isEnd;
	}
	private boolean isEndTag(Token toke) {
		return toke instanceof TagToken && ((TagToken) toke).isEnd;
	}
	
	private void insertComment(CommentToken comment, Node location) {
		Node adjusted = location==null?getNodeInsertionLocation(null):location;
		insert(location, new CommentImpl(getNodeDocument(adjusted), comment.data));
	}

	private void insert(Node location, Node child) {
		location.appendChild(child);
	}

	private Node getNodeInsertionLocation(Node override) {
		Node target = override!=null?override:getCurrentNode();
		if (fostering) {
			//TODO:
		}
		//TODO
		return target;
	}
	
	private Element insertElement(TagToken toke, String namespace) {
		Node ail = getNodeInsertionLocation(null);
		Element el = createElement(toke, namespace, ail);
		//TODO:
		ail.appendChild(el);
		elements.push(el);
		return el;
	}

	private Node getCurrentNode() {
		if (elements.isEmpty()) return document;
		return elements.peek();
	}
	
	private Element createElement(TagToken toke, String namespace, Node parent) {
		DocumentImpl document = getNodeDocument(parent);
		
		//TODO: Custom element code
		return createElement(document, toke.name.toString(), namespace, null, toke.is, false);
	}
	private Element createElement_1(String name, Node parent) {
		DocumentImpl document = getNodeDocument(parent);
		return createElement(document, name, HTML_NAMESPACE, null, null, false);
	}
	private Element createElement(DocumentImpl document, String localName, String namespace, String prefix, String is, boolean synccus) {
		//TODO: a lot of stupid logic
		Element el = ElementFactory.element(namespace, localName, document);
		el.setNamespaceURI(namespace);
		el.setPrefix(prefix);
		el.setLocalName(localName);
		el.setTagName(localName); //?
		el.setIs(is);
		return el;
	}
	
	private void insertCharacter(char data) {
		Node parent = getNodeInsertionLocation(null);
		if (parent instanceof DocumentImpl) return;
		Node lc = parent.getLastChild();
		if (!(lc instanceof TextNodeImpl)) {
			lc = new TextNodeImpl(getNodeDocument(parent));
			parent.appendChild(lc);
		}
		((TextNodeImpl) lc).wholeText.appendCodePoint(data);
	}
	
	private DocumentImpl getNodeDocument(Node n) {
		DocumentImpl nd = n.getOwnerDocument();
		if (nd==null) nd = (DocumentImpl) n;
		return nd;
	}
}