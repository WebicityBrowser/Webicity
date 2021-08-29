package everyos.browser.jhtml.parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.UnsupportedEncodingException;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

import everyos.browser.javadom.imp.JDComment;
import everyos.browser.javadom.imp.JDDocumentBuilder;
import everyos.browser.javadom.imp.JDText;
import everyos.browser.javadom.intf.Document;
import everyos.browser.javadom.intf.Element;
import everyos.browser.javadom.intf.Node;
import everyos.browser.javadom.intf.Text;
import everyos.browser.jhtml.intf.HTMLStyleElement;

//TODO: Do not fire mutation events
//TODO: http://finance.yahoo.com/news/study-reveals-city-worst-traffic-223420982.html cuts off first letter (at parser level)

public final class JHTMLParser {
	public static final String HTML_NAMESPACE = "http://www.w3.org/1999/xhtml";
	private static final char rep_char = '\uFFFD';
	private static String[] specialHTML = new String[] {
		"address", "applet", "area", "article", "aside", "base", "basefont", "bgsound", "blockquote",
		"body", "br", "button", "caption", "center", "col", "colgroup", "dd", "details", "dir", "div",
		"dl", "dt", "embed", "fieldset", "figcaption", "figure", "footer", "form", "frame", "frameset",
		"h1", "h2", "h3", "h4", "h5", "h6", "head", "header", "hgroup", "hr", "html", "iframe", "img",
		"input", "keygen", "li", "link", "listing", "main", "marquee", "menu", "meta", "nav", "noembed",
		"noframes", "noscript", "object", "ol", "p", "param", "plaintext", "pre", "script", "section",
		"select", "source", "style", "summary", "table", "tbody", "td", "template", "textarea", "tfoot",
		"th", "thead", "title", "tr", "track", "ul", "wbr", "xmp"
	};
	
	private InsertionState istate = InsertionState.INITIAL;
	private InsertionState ostate = null;
	private TokenizeState state = TokenizeState.DATA;
	private Document document;
	private Stack<Element> elements = new Stack<Element>();
	private Element head;
	private boolean fostering = false;
	private PushbackReader reader;
	private TokenizeState returnState = null;
	private Token token = null;
	private StringBuilder tmp_buf = new StringBuilder();
	private boolean eof = false;
	private String lastName;
	private UnicodeHelper unicodeHelper = new UnicodeHelper();
	
			
	public JHTMLParser(InputStream stream) throws UnsupportedEncodingException {
		this.reader = new PushbackReader(new InputStreamReader(new BufferedInputStream(stream), "UTF-8"), 32);
		this.document = new JDDocumentBuilder()
			.setType(Document.HTML)
			.setContentType("text/html")
			.build();
	}
	
	public Document getDocument() {
		return document;
	}

	public boolean parse() throws IOException {
		while(!eof) {
			parseChunk();
		}
		return eof;
	}
	
	//TODO: Debug why parsing WhatWG HTML Spec is totally broken
	public void parseChunk() throws IOException {
		while (!eof) {
			int ich = reader.read();
			
			eof = eof|ich==-1;
			int ch = eof?'\0':ich;
			
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
						reader.unread(ch);
						state = TokenizeState.TAG_NAME;
					} else if (ch=='?') {
						token = new CommentToken("");
						state = TokenizeState.BOGUS_COMMENT;
					} else if (eof) {
						emit(new CharToken('<'));
						emit(new EOFToken());
					} else {
						emit(new CharToken('<'));
						reader.unread(ch);
						state = TokenizeState.DATA;
					}
					break;
					
				case END_TAG_OPEN:
					if (Character.isAlphabetic(ch)) {
						token = new TagToken("", true);
						reader.unread(ch);
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
						((TagToken) token).getNameBuilder().appendCodePoint(ch+32);
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						((TagToken) token).getNameBuilder().append(rep_char);
					} else {
						((TagToken) token).getNameBuilder().appendCodePoint(ch);
					}
					break;
					
				case RCDATA_LT_SIGN:
					if (ch=='/') {
						tmp_buf = new StringBuilder();
						state = TokenizeState.RCDATA_END_TAG_OPEN;
					} else {
						emit(new CharToken('<'));
						reader.unread(ch);
						state = TokenizeState.RCDATA;
					}
					break;
					
				case RCDATA_END_TAG_OPEN:
					if (Character.isAlphabetic(ch)) {
						token = new TagToken("", true);
						reader.unread(ch);
						state = TokenizeState.RCDATA_END_TAG_NAME;
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						reader.unread(ch);
						state = TokenizeState.RCDATA;
					}
					break;
					
				case RCDATA_END_TAG_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1&&token instanceof TagToken) {
						state = TokenizeState.BEFORE_ATTRIBUTE_NAME;
					} else if (ch=='/'&&token instanceof TagToken) {
						state = TokenizeState.SELF_CLOSING_START_TAG;
					} else if (ch=='>'&&isAppropriateEndTagToken(token)) {
						state = TokenizeState.DATA;
						emit(token);
					} else if (Character.isAlphabetic(ch)) {
						((TagToken) token).getNameBuilder().appendCodePoint(Character.toLowerCase(ch));
						tmp_buf.appendCodePoint(ch);
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						for (int i=0; i<tmp_buf.length(); i++) emit(new CharToken(tmp_buf.charAt(i)));
						reader.unread(ch);
						state = TokenizeState.RCDATA;
					}
					break;
					
				case RAWTEXT_LT_SIGN:
					if (ch=='/') {
						tmp_buf = new StringBuilder();
						state = TokenizeState.RAWTEXT_END_TAG_OPEN;
					} else {
						emit(new CharToken('<'));
						reader.unread(ch);
						state = TokenizeState.RAWTEXT;
					}
					break;
					
				case RAWTEXT_END_TAG_OPEN:
					if (Character.isAlphabetic(ch)) {
						token = new TagToken("", true);
						reader.unread(ch);
						state = TokenizeState.RAWTEXT_END_TAG_NAME;
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						reader.unread(ch);
						state = TokenizeState.RAWTEXT;
					}
					break;
					
				case RAWTEXT_END_TAG_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1&&isAppropriateEndTagToken(token)) {
						state = TokenizeState.BEFORE_ATTRIBUTE_NAME;
					} else if (ch=='/'&&isAppropriateEndTagToken(token)) {
						state = TokenizeState.SELF_CLOSING_START_TAG;
					} else if (ch=='>'&&isAppropriateEndTagToken(token)) {
						state = TokenizeState.DATA;
						emit(token);
					} else if (Character.isAlphabetic(ch)) {
						((TagToken) token).getNameBuilder().appendCodePoint(Character.toLowerCase(ch));
						tmp_buf.appendCodePoint(ch);
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						for (int i=0; i<tmp_buf.length(); i++) emit(new CharToken(tmp_buf.charAt(i)));
						reader.unread(ch);
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
						reader.unread(ch);
						state = TokenizeState.SCRIPT_DATA;
					}
					break;
					
				case SCRIPT_DATA_END_TAG_OPEN:
					if (Character.isAlphabetic(ch)) {
						token = new TagToken("", true);
						reader.unread(ch);
						state = TokenizeState.SCRIPT_DATA_END_TAG_NAME;
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						reader.unread(ch);
						state = TokenizeState.SCRIPT_DATA;
					}
					break;
					
				case SCRIPT_DATA_END_TAG_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1&&isAppropriateEndTagToken(token)) {
						state = TokenizeState.BEFORE_ATTRIBUTE_NAME;
					} else if (ch=='/'&&isAppropriateEndTagToken(token)) {
						state = TokenizeState.SELF_CLOSING_START_TAG;
					} else if (ch=='>'&&isAppropriateEndTagToken(token)) {
						state = TokenizeState.DATA;
						emit(token);
					} else if (Character.isAlphabetic(ch)) {
						((TagToken) token).getNameBuilder().appendCodePoint(Character.toLowerCase(ch));
						tmp_buf.appendCodePoint(ch);
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						for (int i=0; i<tmp_buf.length(); i++) emit(new CharToken(tmp_buf.charAt(i)));
						reader.unread(ch);
						state = TokenizeState.SCRIPT_DATA;
					}
					break;
					
				case SCRIPT_DATA_ESCAPE_START:
					if (ch=='-') {
						state = TokenizeState.SCRIPT_DATA_ESCAPE_START_DASH;
						emit(new CharToken('-'));
					} else {
						reader.unread(ch);
						state = TokenizeState.SCRIPT_DATA;
					}
					break;
					
				case SCRIPT_DATA_ESCAPE_START_DASH:
					if (ch=='-') {
						state = TokenizeState.SCRIPT_DATA_ESCAPED_DASH_DASH;
						emit(new CharToken('-'));
					} else {
						reader.unread(ch);
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
						reader.unread(ch);
						state = TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPE_START;
					} else {
						emit(new CharToken('<'));
						reader.unread(ch);
						state = TokenizeState.SCRIPT_DATA_ESCAPED;
					}
					break;
					
				case SCRIPT_DATA_ESCAPED_END_TAG_OPEN:
					if (Character.isAlphabetic(ch)) {
						token = new TagToken("", true);
						reader.unread(ch);
						state = TokenizeState.SCRIPT_DATA_ESCAPED_END_TAG_NAME;
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						reader.unread(ch);
						state = TokenizeState.SCRIPT_DATA_ESCAPED;
					}
					break;
					
				case SCRIPT_DATA_ESCAPED_END_TAG_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1&&isAppropriateEndTagToken(token)) {
						state = TokenizeState.BEFORE_ATTRIBUTE_NAME;
					} else if (ch=='/'&&isAppropriateEndTagToken(token)) {
						state = TokenizeState.SELF_CLOSING_START_TAG;
					} else if (ch=='>'&&isAppropriateEndTagToken(token)) {
						state = TokenizeState.DATA;
					} else if (Character.isAlphabetic(ch)) {
						((TagToken) token).getNameBuilder().append(Character.toLowerCase(ch));
						tmp_buf.appendCodePoint(ch);
					} else {
						emit(new CharToken('<'));
						emit(new CharToken('/'));
						for (int i=0; i<tmp_buf.length(); i++) emit(new CharToken(tmp_buf.charAt(i)));
						reader.unread(ch);
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
						tmp_buf.appendCodePoint(Character.toLowerCase(ch));
						emit(new CharToken(ch));
					} else {
						reader.unread(ch);
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
						reader.unread(ch);
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
						tmp_buf.appendCodePoint(Character.toLowerCase(ch));
						emit(new CharToken(ch));
					} else {
						reader.unread(ch);
						state = TokenizeState.SCRIPT_DATA_DOUBLE_ESCAPED;
					}
					break;
					
				case BEFORE_ATTRIBUTE_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1) {
					} else if (ch=='/'||ch=='>'||eof) {
						reader.unread(ch);
						state = TokenizeState.AFTER_ATTRIBUTE_NAME;
					} else if (ch=='=') {
						((TagToken) token).resetAttributeNameBuilder();
						((TagToken) token).getAttributeNameBuilder().appendCodePoint(ch);
						((TagToken) token).resetAttributeValueBuilder();
						state = TokenizeState.ATTRIBUTE_NAME;
					} else {
						((TagToken) token).resetAttributeNameBuilder();
						((TagToken) token).resetAttributeValueBuilder();
						reader.unread(ch);
						state = TokenizeState.ATTRIBUTE_NAME;
					}
					break;
					
				case ATTRIBUTE_NAME:
					//TODO: Finalize attr
					if (("\t\n\f />").indexOf(ch)!=-1||eof) {
						reader.unread(ch);
						state = TokenizeState.AFTER_ATTRIBUTE_NAME;
					} else if (ch=='=') {
						state = TokenizeState.BEFORE_ATTRIBUTE_VALUE;
					} else if (ch=='\0') {
						((TagToken) token).getAttributeNameBuilder().append(rep_char);
					} else {
						((TagToken) token).getAttributeNameBuilder().appendCodePoint(Character.toLowerCase(ch));
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
						((TagToken) token).resetAttributeNameBuilder();
						((TagToken) token).resetAttributeValueBuilder();
						reader.unread(ch);
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
						reader.unread(ch);
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
						((TagToken) token).getAttributeValueBuilder().appendCodePoint(rep_char);
					} else {
						((TagToken) token).getAttributeValueBuilder().appendCodePoint(ch);
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
						((TagToken) token).getAttributeValueBuilder().appendCodePoint(rep_char);
					} else {
						((TagToken) token).getAttributeValueBuilder().appendCodePoint(ch);
					}
					break;
					
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
						((TagToken) token).getAttributeValueBuilder().append(rep_char);
					} else {
						((TagToken) token).getAttributeValueBuilder().appendCodePoint(ch);
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
						reader.unread(ch);
						state = TokenizeState.BEFORE_ATTRIBUTE_NAME;
					}
					break;
					
				case SELF_CLOSING_START_TAG:
					if (ch=='>') {
						((TagToken) token).setSelfClosing(true);
						state = TokenizeState.DATA;
						emit(token);
					} else if (eof) {
						emit(new EOFToken());
					} else {
						reader.unread(ch);
						state = TokenizeState.BEFORE_ATTRIBUTE_NAME;
					}
					break;
					
				case BOGUS_COMMENT:
					if (ch=='>') {
						state = TokenizeState.DATA;
					} else if (eof) {
						emit(new EOFToken());
					} else if (ch=='\0') {
						((CommentToken) token).getDataBuilder().append(rep_char);
					} else {
						((CommentToken) token).getDataBuilder().append((char) ch);
					}
					break;
					
				case MARKUP_DECLARATION_OPEN:
					reader.unread(ch);
					if (consumeIfEquals(reader, "--")) {
						token = new CommentToken("");
						state = TokenizeState.COMMENT_START;
					} else if (consumeIfEqualsCI(reader, "DOCTYPE")) {
						state = TokenizeState.DOCTYPE;
					} else if (consumeIfEquals(reader, "[CDATA[")) {
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
						reader.unread(ch);
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
						((CommentToken) token).getDataBuilder().append('-');
						reader.unread(ch);
						state = TokenizeState.COMMENT;
					}
					break;
					
				case COMMENT:
					if (ch=='<') {
						((CommentToken) token).getDataBuilder().append((char) ch);
						state = TokenizeState.COMMENT_LT_SIGN;
					} else if (ch=='-') {
						state = TokenizeState.COMMENT_END_DASH;
					} else if (eof) {
						emit(token);
						emit(new EOFToken());
					} else if (ch=='\0') {
						((CommentToken) token).getDataBuilder().append(rep_char);
					} else {
						((CommentToken) token).getDataBuilder().append((char) ch);
					}
					break;
					
				case COMMENT_LT_SIGN:
					if (ch=='!') {
						((CommentToken) token).getDataBuilder().append((char) ch);
						state = TokenizeState.COMMENT_LT_SIGN_BANG;
					} else if (ch=='<') {
						((CommentToken) token).getDataBuilder().append((char) ch);
					} else {
						reader.unread(ch);
						state = TokenizeState.DATA;
					}
					break;
					
				case COMMENT_LT_SIGN_BANG:
					if (ch=='-') {
						state = TokenizeState.COMMENT_LT_SIGN_BANG_DASH;
					} else {
						reader.unread(ch);
						state = TokenizeState.COMMENT;
					}
					break;
					
				case COMMENT_LT_SIGN_BANG_DASH:
					if (ch=='-') {
						state = TokenizeState.COMMENT_LT_SIGN_BANG_DASH_DASH;
					} else {
						reader.unread(ch);
						state = TokenizeState.COMMENT_END_DASH;
					}
					break;
					
				case COMMENT_LT_SIGN_BANG_DASH_DASH:
					reader.unread(ch);
					state = TokenizeState.COMMENT_END;
					break;
					
				case COMMENT_END_DASH:
					if (ch=='-') {
						state = TokenizeState.COMMENT_END;
					} else if (eof) {
						emit(token);
						emit(new EOFToken());
					} else {
						((CommentToken) token).getDataBuilder().append('-');
						reader.unread(ch);
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
						((CommentToken) token).getDataBuilder().append('-');
					} else if (eof) {
						emit(token);
						emit(new EOFToken());
					} else {
						emit(new CharToken('-'));
						emit(new CharToken('-'));
						reader.unread(ch);
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
						reader.unread(ch);
						state = TokenizeState.COMMENT;
					}
					break;
					
				case DOCTYPE:
					if (("\t\n\f ").indexOf(ch)!=-1) {
						state = TokenizeState.BEFORE_DOCTYPE_NAME;
					} else if (ch=='>') {
						reader.unread(ch);
						state = TokenizeState.BEFORE_DOCTYPE_NAME;
					} else if (eof) {
						DoctypeToken toke = new DoctypeToken();
						toke.setForceQuirks(true);
						emit(toke);
						emit(new EOFToken());
					} else {
						reader.unread(ch);
						state = TokenizeState.BEFORE_DOCTYPE_NAME;
					}
					break;
					
				case BEFORE_DOCTYPE_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1) {
					} else if (eof) {
						DoctypeToken toke = new DoctypeToken();
						toke.setForceQuirks(true);
						emit(toke);
						emit(new EOFToken());
					} else if (ch=='\0') {
						DoctypeToken toke = new DoctypeToken();
						toke.getNameBuilder().append(rep_char);
						token = toke;
						state = TokenizeState.DATA;
					} else if (ch=='>') {
						DoctypeToken toke = new DoctypeToken();
						toke.setForceQuirks(true);
						state = TokenizeState.DATA;
						emit(toke);
					} else {
						DoctypeToken toke = new DoctypeToken();
						toke.getNameBuilder().append(String.valueOf(ch).toLowerCase());
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
						((DoctypeToken) token).setForceQuirks(true);
						emit(token);
						emit(new EOFToken());
					} else {
						((DoctypeToken) token).getNameBuilder().append(ch=='\0'?rep_char:(char) ch);
					}
					break;
					
					
				case AFTER_DOCTYPE_NAME:
					if (("\t\n\f ").indexOf(ch)!=-1) {
						
					} else if (ch=='>') {
						state = TokenizeState.DATA;
						emit(token);
					} else if (eof) {
						((DoctypeToken) token).setForceQuirks(true);
						emit(token);
						emit(new EOFToken());
					} else if (consumeIfEqualsCI(reader, "PUBLIC")) {
						state = TokenizeState.AFTER_DOCTYPE_PUBLIC_KEYWORD;
					} else if (consumeIfEqualsCI(reader, "SYSTEM")) {
						state = TokenizeState.AFTER_DOCTYPE_SYSTEM_KEYWORD;
					} else {
						((DoctypeToken) token).setForceQuirks(true);
						reader.unread(ch);
						state = TokenizeState.BOGUS_DOCTYPE;
					}
					break;
					
				case AFTER_DOCTYPE_PUBLIC_KEYWORD:
					if (("\t\n\f ").indexOf(ch)!=-1) {
						state = TokenizeState.BEFORE_DOCTYPE_PUBLIC_IDENTIFIER;
					} else if (ch=='"') {
						((DoctypeToken) token).setPublicIdentifier("");
						state = TokenizeState.DOCTYPE_PUBLIC_IDENTIFIER_DQ;
					} else if (ch=='\'') {
						((DoctypeToken) token).setPublicIdentifier("");
						state = TokenizeState.DOCTYPE_PUBLIC_IDENTIFIER_SQ;
					} else if (ch=='>') {
						((DoctypeToken) token).setForceQuirks(true);
						state = TokenizeState.DATA;
						emit(token);
					} else if (eof) {
						((DoctypeToken) token).setForceQuirks(true);
						emit(token);
						emit(new EOFToken());
					} else {
						((DoctypeToken) token).setPublicIdentifier("");
						reader.unread(ch);
						state = TokenizeState.BOGUS_DOCTYPE;
					}
					break;
					
				case BEFORE_DOCTYPE_PUBLIC_IDENTIFIER:
					if (("\t\n\f ").indexOf(ch)!=-1) {
						
					} else if (ch=='"') {
						((DoctypeToken) token).setPublicIdentifier("");
						state = TokenizeState.DOCTYPE_PUBLIC_IDENTIFIER_DQ;
					} else if (ch=='\'') {
						((DoctypeToken) token).setPublicIdentifier("");
						state = TokenizeState.DOCTYPE_PUBLIC_IDENTIFIER_SQ;
					} else if (ch=='>') {
						((DoctypeToken) token).setForceQuirks(true);
						state = TokenizeState.DATA;
						emit(token);
					} else if (eof) {
						((DoctypeToken) token).setForceQuirks(true);
						emit(token);
						emit(new EOFToken());
					} else {
						((DoctypeToken) token).setPublicIdentifier("");
						reader.unread(ch);
						state = TokenizeState.BOGUS_DOCTYPE;
					}
					break;
					
				case DOCTYPE_PUBLIC_IDENTIFIER_DQ:
					if (ch=='"') {
						state = TokenizeState.AFTER_DOCTYPE_PUBLIC_IDENTIFIER;
					} else if (ch=='\0') {
						//TODO: Append rep char
					} else if (ch=='>') {
						((DoctypeToken) token).setForceQuirks(true);
						state = TokenizeState.DATA;
						emit(token);
					} else if (eof) {
						((DoctypeToken) token).setForceQuirks(true);
						emit(token);
						emit(new EOFToken());
					} else {
						//TODO: Append char
					}
					break;
					
				case DOCTYPE_PUBLIC_IDENTIFIER_SQ:
					if (ch=='\'') {
						state = TokenizeState.AFTER_DOCTYPE_PUBLIC_IDENTIFIER;
					} else if (ch=='\0') {
						//TODO: Append rep char
					} else if (ch=='>') {
						((DoctypeToken) token).setForceQuirks(true);
						state = TokenizeState.DATA;
						emit(token);
					} else if (eof) {
						((DoctypeToken) token).setForceQuirks(true);
						emit(token);
						emit(new EOFToken());
					} else {
						//TODO: Append char
					}
					break;
					
				case AFTER_DOCTYPE_PUBLIC_IDENTIFIER:
					if (("\t\n\f ").indexOf(ch)!=-1) {
						state = TokenizeState.BETWEEN_DOCTYPE_PUBLIC_AND_SYSTEM_IDENTIFIERS;
					} else if (ch=='>') {
						state = TokenizeState.DATA;
						emit(token);
					} else if (ch=='"') {
						((DoctypeToken) token).setSystemIdentifier("");
						state = TokenizeState.DOCTYPE_SYSTEM_IDENTIFIER_DQ;
					} else if (ch=='\'') {
						((DoctypeToken) token).setSystemIdentifier("");
						state = TokenizeState.DOCTYPE_SYSTEM_IDENTIFIER_SQ;
					} else if (eof) {
						((DoctypeToken) token).setForceQuirks(true);
						emit(token);
						emit (new EOFToken());
					} else {
						((DoctypeToken) token).setForceQuirks(true);
						reader.unread(ch);
						state = TokenizeState.BOGUS_DOCTYPE;
					}
					break;
					
				case BETWEEN_DOCTYPE_PUBLIC_AND_SYSTEM_IDENTIFIERS:
					if (("\t\n\f ").indexOf(ch)!=-1) {
						
					} else if (ch=='>') {
						state = TokenizeState.DATA;
						emit(token);
					} else if (ch=='"') {
						((DoctypeToken) token).setSystemIdentifier("");
						state = TokenizeState.DOCTYPE_SYSTEM_IDENTIFIER_DQ;
					} else if (ch=='\'') {
						((DoctypeToken) token).setSystemIdentifier("");
						state = TokenizeState.DOCTYPE_SYSTEM_IDENTIFIER_SQ;
					} else if (eof) {
						((DoctypeToken) token).setForceQuirks(true);
						emit(token);
						emit (new EOFToken());
					} else {
						((DoctypeToken) token).setForceQuirks(true);
						reader.unread(ch);
						state = TokenizeState.BOGUS_DOCTYPE;
					}
					break;
					
				case AFTER_DOCTYPE_SYSTEM_KEYWORD:
					if (("\t\n\f ").indexOf(ch)!=-1) {
						state = TokenizeState.BEFORE_DOCTYPE_SYSTEM_IDENTIFIER;
					} else if (ch=='"') {
						((DoctypeToken) token).setSystemIdentifier("");
						state = TokenizeState.DOCTYPE_SYSTEM_IDENTIFIER_DQ;
					} else if (ch=='\'') {
						((DoctypeToken) token).setSystemIdentifier("");
						state = TokenizeState.DOCTYPE_SYSTEM_IDENTIFIER_SQ;
					} else if (ch=='>') {
						((DoctypeToken) token).setForceQuirks(true);
						state = TokenizeState.DATA;
						emit(token);
					} else if (eof) {
						((DoctypeToken) token).setForceQuirks(true);
						emit(token);
						emit (new EOFToken());
					} else {
						((DoctypeToken) token).setForceQuirks(true);
						reader.unread(ch);
						state = TokenizeState.BOGUS_DOCTYPE;
					}
					break;
					
				//TODO: 64 onwards
					
				//72
				case CHARACTER_REFERENCE:
					tmp_buf = new StringBuilder("&");
					if (Character.isAlphabetic(ch) || Character.isDigit(ch)) {
						reader.unread(ch);
						state = TokenizeState.NAMED_CHARACTER_REFERENCE;
					} else if (ch=='#') {
						tmp_buf.appendCodePoint(ch);
						state = TokenizeState.NUMERIC_CHARACTER_REFERENCE;
					} else {
						flushCodePointsConsumedAsACharacterReference();
						reader.unread(ch);
						state = returnState;
					}
					break;
					
				case NAMED_CHARACTER_REFERENCE:
					reader.unread(ch);
					
					String foundRef = null;
					int longest = 0;
					for (String ref: unicodeHelper.getEntityNames()) {
						if (ref.length() > longest && ref.equals('&'+new String(peek(reader, ref.length()-1)))) {
							longest = ref.length();
							foundRef = ref;
						}
					}
					
					if (foundRef!=null) {
						//TODO: Not codepoint safe
						reader.read(new char[foundRef.length()-1]);
						
						tmp_buf = new StringBuilder();
						for (int ch2: unicodeHelper.getCodePointsForNamedEntity(foundRef)) {
							tmp_buf.appendCodePoint(ch2);
						}
						flushCodePointsConsumedAsACharacterReference();
						state = returnState;
					} else {
						flushCodePointsConsumedAsACharacterReference();
						state = returnState;//TokenizeState.AMBIGUOUS_AMPERSAND;
					}
					
					break;
					
				case NUMERIC_CHARACTER_REFERENCE:
					//TODO
					state = returnState;
					break;
					
				default:
					break;
			}
		}
		
		while (!elements.isEmpty()) {
			pop();
		}
	}

	private void flushCodePointsConsumedAsACharacterReference() {
		if (isConsumedAsPartOfAnAttribute()) {
			//TODO
		} else {
			for (int i=0; i<tmp_buf.length(); i++) {
				emit(new CharToken(tmp_buf.codePointAt(i)));
			}
		}
		
		//TODO
	}
	
	private boolean isConsumedAsPartOfAnAttribute() {
		return 
			returnState == TokenizeState.ATTRIBUTE_VALUE_DQ ||
			returnState == TokenizeState.ATTRIBUTE_VALUE_SQ ||
			returnState == TokenizeState.ATTRIBUTE_VALUE_UQ;
	}

	private char[] peek(PushbackReader stream, int len) throws IOException {
		char[] b = new char[len];
		stream.read(b, 0, len);
		stream.unread(b);
		return b;
	}
	private boolean consumeIfEquals(PushbackReader stream, String s) throws IOException {
		// TODO: Not codepoint safe
		char[] b = new char[s.length()];
		stream.read(b, 0, s.length());
		if (new String(b).equals(s)) {
			return true;
		}
		stream.unread(b);
		return false;
	}
	private boolean consumeIfEqualsCI(PushbackReader stream, String s) throws IOException {
		char[] b = new char[s.length()];
		stream.read(b, 0, s.length());
		if (new String(b).equalsIgnoreCase(s)) {
			return true;
		}
		stream.unread(b);
		return false;
	}

	private void emit(final InsertionState cstate, final Token token) {
		String name = null;
		if (token instanceof TagToken) {
			name = ((TagToken) token).getNameBuilder().toString();
			if (isStartTag(token)) {
				this.lastName = name;
			}
		}
		switch(cstate) {
			case INITIAL:
				if (isWhitespace(token)) {	
				} else if (token instanceof CommentToken) {
					insertComment((CommentToken) token, document);
				} else if (token instanceof DoctypeToken) {
					//TODO
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
					istate = InsertionState.BEFORE_HEAD;
				} else if (isEndTag(token) && !tagIs(name, "head", "body", "html", "br")) {
				} else {
					Element el = createElement(new TagToken("html", false), HTML_NAMESPACE, document);
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
					head = insertElement((TagToken) token, HTML_NAMESPACE);
					istate = InsertionState.IN_HEAD;
				} else if (isEndTag(token) && !tagIs(name, "head", "body", "html", "br")) {
				} else {
					head = insertElement(new TagToken("head", true), HTML_NAMESPACE);
					istate = InsertionState.IN_HEAD;
					emit(token);
				}
				break;
			case IN_HEAD:
				if (isWhitespace(token)) {
					insertCharacter(((CharToken)token).getCharacter());
				} else if (token instanceof CommentToken) {
					insertComment((CommentToken) token);
				} else if (token instanceof DoctypeToken) {
				} else if (isStartTag(token) && name.equals("html")) {
					emit(InsertionState.IN_BODY, token);
				} else if (isStartTag(token)&&tagIs(name, "base", "basefont", "bgsound", "link")) {
					insertElement((TagToken) token, HTML_NAMESPACE);
					pop();
					//TODO: "Acknowledge" the element
				} else if (isStartTag(token) && name.equals("meta")) {
					insertElement((TagToken) token, HTML_NAMESPACE);
					pop();
					//TODO: "Acknowledge" the element
					//TODO: Encoding games
				} else if (isStartTag(token) && name.equals("title")) {
					parseGenericRCDATA((TagToken)token);
				} else if (isStartTag(token) && tagIs(name, "noframes", "style")) {
					//TODO: Handle noscript
					parseGenericRawText((TagToken) token);
				} else if (isStartTag(token) && name.equals("script")) {
					//TODO:
					Node insertion = getNodeInsertionLocation(null);
					Element e = createElement((TagToken) token, HTML_NAMESPACE, insertion);
					elements.push(e);
					state = TokenizeState.SCRIPT_DATA;
					ostate = istate;
					istate = InsertionState.TEXT;
				} else if (isEndTag(token) && name.equals("head")) {
					pop();
					istate = InsertionState.AFTER_HEAD;
				} else if (isEndTag(token) && !tagIs(name,  "body", "html", "br")) {
					/*TODO: Template*/ 
				} else if (isStartTag(token) && name.equals("head") || isEndTag(token)) {
				} else {
					if (!elements.isEmpty()) pop();
					istate = InsertionState.AFTER_HEAD;
					emit(token);
				}
				break;
				
			//TODO: In head noscript
				
			case AFTER_HEAD:
				if (token instanceof CharToken && ("\t\n\f\r ").indexOf(((CharToken) token).getCharacter())!=-1) {
					insertCharacter(((CharToken) token).getCharacter());
				} else if (token instanceof CommentToken) {
					insertComment((CommentToken) token);
				} else if (token instanceof DoctypeToken) {
				} else if (isStartTag(token) && name.equals("html")) {
					emit(InsertionState.IN_BODY, token);
				} else if (isStartTag(token) && name.equals("body")) {
					Element e = insertElement((TagToken) token, HTML_NAMESPACE);
					//TODO
					istate = InsertionState.IN_BODY;
				} else if (isStartTag(token) && name.equals("frameset")) {
					insertElement((TagToken) token, HTML_NAMESPACE);
					istate = InsertionState.IN_FRAMESET;
				} else if (isStartTag(token) && tagIs(name, "base", "basefont", "bgsound", "link", "meta", "noframes", "script", "style", "template", "title")) {
					elements.push(head);
					emit(InsertionState.IN_HEAD, token);
					while (pop()!=head); //TODO: Am I supposed to remove only head? Or all elements afterwards?
				} else if (isEndTag(token) && name.equals("template")) {
					emit(InsertionState.IN_HEAD, token);
				} else if ((isStartTag(token)&&name.equals("head")) || (isEndTag(token)&&!tagIs(name, "body", "html", "br"))) {
				} else {
					insertElement(new TagToken("body", false), HTML_NAMESPACE);
					istate = InsertionState.IN_BODY;
					emit(token);
				}
				break;
				
			case IN_BODY:
				if (token instanceof CharToken) {
					//TODO: All char processing
					char data = ((CharToken) token).getCharacter();
					if (data=='\0') break;
					insertCharacter(data);
				} else if (token instanceof CommentToken) {
					insertComment((CommentToken) token);
				} else if (token instanceof DoctypeToken) {
				} else if (isStartTag(token) && name.equals("html")) {
					// TODO
				} else if ((isStartTag(token) && tagIs(name, "base", "basefont", "bgsound", "link", 
						"meta", "noframes", "script", "style", /*"template",*/ "title"))
						/*|| (isEndTag(token) && name.equals("template"))*/) {
					//TODO
					emit(InsertionState.IN_HEAD, token);
				} else if (isStartTag(token) && name.equals("body")) {
					//TODO
				} else if (isStartTag(token) && name.equals("frameset")) {
					//TODO
				} else if (token instanceof EOFToken) {
					//TODO
					break;
				} else if (isEndTag(token) && name.equals("body")) {
					//TODO
					istate = InsertionState.AFTER_BODY;
				} else if (isEndTag(token) && name.equals("html")) {
					//TODO
					istate = InsertionState.AFTER_BODY;
					emit(token);
				} else if (isStartTag(token) && tagIs(name, "address", "article", "aside", "blockquote", "center", "details",
						"dialog", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "main", "menu",
						"nav", "ol", "p", "section", "summary", "ul")) {
					
					//TODO: Button in scope
					insertElement((TagToken) token, HTML_NAMESPACE);
				} else if (isStartTag(token) && tagIs(name, "h1", "h2", "h3", "h4", "h5", "h6")) {
					//TODO: Close p element if in button scope
					try {
						Element top = elements.peek();
						if (elementIs(top, HTML_NAMESPACE, "h1", "h2", "h3", "h4", "h5", "h6")) {
							pop();
						}
					} catch (EmptyStackException e) {}
					
					insertElement((TagToken) token, HTML_NAMESPACE);
				} else if (isStartTag(token) && tagIs(name, "pre", "listing")) {
					//TODO
					insertElement((TagToken) token, HTML_NAMESPACE);
					
					//TODO: Yet more html tags
				} else if (isEndTag(token) && tagIs(name, "address", "article", "aside", "blockquote", "center", "details",
						"dialog", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "main", "menu",
						"nav", "ol", "p", "section", "summary", "ul")) {
					
					
					//TODO: Not at all spec compliant
					//System.out.println(name);
					Stack<Element> s = new Stack<>();
					while (!elements.isEmpty()) {
						//System.out.println(elements.size());
						s.add(pop());
						if (s.peek().getTagName().equals(name)) break;
						if (elements.isEmpty()) {
							while(!s.isEmpty()) elements.push(s.pop());
							break;
						}
					}
					//while(!pop().tagName.equals(name)) {}
					
					//TODO: Many things in between
				} else if (isStartTag(token) && name.equals("a")) {
					//TODO: Active elements
					insertElement((TagToken) token, HTML_NAMESPACE);
					
					//TODO: Many things in between
				} else if (isEndTag(token) && name.equals("a")) {
					//TODO: adoption agency algorithm
					
					Stack<Element> s = new Stack<>();
					while (!elements.isEmpty()) {
						s.add(pop());
						if (s.peek().getTagName().equals(name)) break;
						if (elements.isEmpty()) {
							while(!s.isEmpty()) elements.push(s.pop());
							break;
						}
					}
					//TODO: Many things in between
				} else if (isStartTag(token)) {
					//TODO: Reconstruct active formatting elements
					insertElement((TagToken) token, HTML_NAMESPACE);
				} else if (isEndTag(token)) {
					int pos = elements.size();
					while (true) {
						pos--;
						Element el = elements.get(pos);
						if (elementIs(el, HTML_NAMESPACE, name)) {
							generateEmpliedEndTags(name);
							while (!pop().equals(el));
							break;
						} else if (isSpecial(el.getNamespaceURI(), el.getTagName())) {
							break;
						}
					}
				}
				break;
				
			case TEXT:
				//TODO:
				if (token instanceof CharToken) {
					insertCharacter(((CharToken) token).getCharacter());
				} else if (isEndTag(token) && name.equals("script")) {
					Element e = elements.isEmpty()?null:pop();
					//TODO
					istate = ostate;
				} else if (isEndTag(token)) {
					pop();
					istate = ostate;
				}
				break;
		}
	}

	private void insertComment(CommentToken token2) {
		// TODO Auto-generated method stub
		
	}

	private void emit(Token token) {
		emit(istate, token);
	}
	
	private boolean tagIs(String tname, String... names) {
		for (String name: names) {
			if (tname.hashCode()==name.hashCode()&&tname.equals(name)) return true;
		}
		return false;
	}
	private boolean isWhitespace(Token token) {
		return token instanceof CharToken && ("\t\n\f\r ").indexOf(((CharToken) token).getCharacter())!=-1;
	}
	private boolean isStartTag(Token toke) {
		return toke instanceof TagToken && !((TagToken) toke).getIsEnd();
	}
	private boolean isEndTag(Token toke) {
		return toke instanceof TagToken && ((TagToken) toke).getIsEnd();
	}
	
	private void insertComment(CommentToken comment, Node location) {
		Node adjusted = location==null?getNodeInsertionLocation(null):location;
		insert(location, new JDComment(getNodeDocument(adjusted), comment.getDataBuilder().toString()));
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
	
	private Element createElement(TagToken token, String namespace, Node parent) {
		Document document = getNodeDocument(parent);
		
		//TODO: Custom element code
		HashMap<String, String> attributes = token.getAttributes();
		Element e = createElement(document, token.getNameBuilder().toString(), namespace, null, attributes.get("is"), false);
		
		attributes.forEach((n, v)->e.setAttribute(n, v));
		
		return e;
	}
	private Element createElement(Document document, String localName, String namespace, String prefix, String is, boolean synccus) {
		//TODO: a lot of logic
		ElementFactory factory = new ElementFactory();
		factory.setDocument(document);
		factory.setNamespaceURI(namespace);
		factory.setPrefix(prefix);
		factory.setLocalName(localName);
		factory.setTagName(localName); //?
		factory.setIs(is);
		return factory.createElement(namespace, localName);
	}
	
	private void insertCharacter(char data) {
		Node parent = getNodeInsertionLocation(null);
		if (parent instanceof Document) return;
		Node lc = parent.getLastChild();
		if (!(lc instanceof Text)) {
			lc = new JDText(getNodeDocument(parent));
			parent.appendChild(lc);
		}
		((Text) lc).appendData(data);
	}
	
	private Document getNodeDocument(Node n) {
		Document nd = n.getOwnerDocument();
		if (nd==null) nd = (Document) n;
		return nd;
	}
	
	private void parseGenericRCDATA(TagToken token) {
		insertElement(token, HTML_NAMESPACE);
		state = TokenizeState.RCDATA;
		ostate = istate;
		istate = InsertionState.TEXT;
	}
	private void parseGenericRawText(TagToken token) {
		insertElement(token, HTML_NAMESPACE);
		state = TokenizeState.RAWTEXT;
		ostate = istate;
		istate = InsertionState.TEXT;
	}
	
	private boolean elementIs(Element element, String namespace, String... options) {
		if (element==null) return false;
		if (!element.getNamespaceURI().equals(namespace)) return false;
		for (String tagName: options) {
			if (tagName.equals(element.getTagName())) return true;
		}
		return false;
	}
	
	private boolean isSpecial(String namespace, String name) {
		if (namespace.equals(HTML_NAMESPACE)) {
			for (String n: specialHTML) {
				if (n.equals(name)) return true;
			}
		}
		return false;
	}
	
	private void generateEmpliedEndTags(String excluded) {
		//TODO
	}
	
	private boolean isAppropriateEndTagToken(Token token) {
		if (!isEndTag(token)) return false;
		return ((TagToken) token).getNameBuilder().toString().equals(lastName);
	}
	
	private Element pop() {
		Element e = elements.pop();
		if (e instanceof HTMLStyleElement) {
			((HTMLStyleElement) e).update();
		}
		return e;
	}
}