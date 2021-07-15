package everyos.browser.jcss.parser;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

public class JCSSParser {
	public CSSToken[] createFromString(String input) throws IOException {
		//TODO: Switch to a proper input stream
		input = input
			.replace("/r/n", "/n")
			.replace("/f",   "/n")
			.replace("/r",   "/n");
		
		return createFromStream(new StringReader(input));
	}
	
	public CSSToken[] createFromStream(Reader reader_) throws IOException {
		//TODO: Replace code points
		
		ArrayList<CSSToken> tokens = new ArrayList<>();
		
		PushbackReader reader = new PushbackReader(reader_, 4);
		
		while (true) {
			CSSToken result = consumeAToken(reader);
			tokens.add(result);
			
			if (result instanceof EOFToken) {
				break;
			}
		}
		
		return tokens.toArray(new CSSToken[tokens.size()]);
	}

	private static CSSToken consumeAToken(PushbackReader reader) throws IOException {
		// TODO: Consume comments
		char ch = (char) reader.read();
		if (Character.isWhitespace(ch)) {
			while (Character.isWhitespace(reader.read()));
			return new WhitespaceToken();
		} else if (ch=='"') {
			return consumeAStringToken(reader, '"');
		} else if (ch=='#') {
			if (isANameCodepoint(peek(reader, 1)[0]) || isAValidEscape(String.valueOf(peek(reader, 2)))) {	
				HashToken token = new HashToken();
				if (startsWithAnIdentifier(ch, reader)) {
					token.setType(TokenType.ID);
				}
				String name = consumeAName(reader);
				token.setValue(name);
				return token;
			} else {
				return new DelimToken(ch);
			}
		} else if (ch == '\'') {
			return consumeAStringToken(reader, ch);
		} else if (ch == '(') {
			return new LParenToken();
		} else if (ch == ')') {
			return new RParenToken();
		} else if (ch == '+') {
			if (startsWithANumber(ch, reader)) {
				reader.unread(ch);
				return consumeANumericToken(reader);
			} else {
				return new DelimToken(ch);
			}
		} else if (ch == ',') {
			return new CommaToken();
		} else if (ch == '-') {
			if (startsWithANumber(ch, reader)) {
				reader.unread(ch);
				return consumeANumericToken(reader);
			} else if (peek(reader, 1)[0] == '-' && peek(reader, 2)[1] == '>') {
				reader.read();
				reader.read();
				return new CDCToken();
			} else if (startsWithAnIdentifier(ch, reader)) {
				reader.unread(ch);
				return consumeAnIdentLikeToken(reader);
			} else {
				return new DelimToken(ch);
			}
		} else if (ch == '.') {
			if (startsWithANumber(ch, reader)) {
				reader.unread(ch);
				return consumeANumericToken(reader);
			} else {
				return new DelimToken(ch);
			}
		} else if (ch == ':') {
			return new ColonToken();
		} else if (ch == ';') {
			return new SemicolonToken();
		} else if (ch == '<') {
			if (peek(reader, 1)[0] == '!' && peek(reader, 2)[1] == '-' && peek(reader, 3)[2] == '-') {
				reader.read(new char[3], 0, 3);
				return new CDOToken();
			} else {
				return new DelimToken(ch);
			}
		} else if (ch == '@') {
			if (startsWithAnIdentifier_(reader)) {
				AtKeywordToken token = new AtKeywordToken();
				token.setValue(consumeAName(reader));
				return token;
			} else {
				return new DelimToken(ch);
			}
		} else if (ch == '[') {
			return new LSBrackToken();
		} else if (ch == '\\') {
			if (startsWithAValidEscape(ch, peek(reader, 1)[0])) {
				reader.unread(ch);
				return consumeAnIdentLikeToken(reader);
			} else {
				return new DelimToken(ch);
			}
		} else if (ch == ']') {
			return new RSBrackToken();
		} else if (ch == '{') {
			return new LCBrackToken();
		} else if (ch == '}') {
			return new RCBrackToken();
		} else if (Character.isDigit(ch)) {
			reader.unread(ch);
			return consumeANumericToken(reader);
		} else if (isNameStartCodepoint(ch)) { //TODO: Skipped a few
			reader.unread(ch);
			return consumeAnIdentLikeToken(reader);
		} else if (ch == -1) {
			return new EOFToken();
		} else {
			return new DelimToken(ch);
		}
	}
	
	private static boolean startsWithAValidEscape(char ch, char c) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean startsWithAnIdentifier_(PushbackReader reader) {
		// TODO Auto-generated method stub
		return false;
	}

	private static CSSToken consumeAnIdentLikeToken(PushbackReader reader) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	private static CSSToken consumeANumericToken(PushbackReader reader) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	private static boolean startsWithANumber(char ch, PushbackReader reader) throws IOException {
		switch(ch) {
			case '+':
			case '-':
				if (Character.isDigit(peek(reader, 1)[0])) {
					return true;
				} else {
					return peek(reader, 1)[0] == '.' && Character.isDigit(peek(reader, 1)[0]);
				}
			case '.':
				return Character.isDigit(peek(reader, 1)[0]);
			default:
				return Character.isDigit(ch);
		}
	}
	
	private static boolean startsWithAnIdentifier(int ch, PushbackReader reader) throws IOException {
		return false;
	}
	
	private static boolean isNameStartCodepoint(int ch) {
		return Character.isAlphabetic(ch) || ch=='_';
	}

	private static String consumeAName(PushbackReader reader) {
		// TODO Auto-generated method stub
		return null;
	}

	private static boolean isAValidEscape(String substring) {
		return substring.charAt(0)=='\\' && substring.charAt(1)!='\n';
	}

	private static boolean isANameCodepoint(char ch) {
		return isANameStartCodepoint(ch) || Character.isDigit(ch) || ch=='-';
	}

	private static boolean isANameStartCodepoint(char ch) {
		return Character.isLetter(ch) || ch>='\u0080' || ch=='_';
	}

	private static StringToken consumeAStringToken(PushbackReader reader, char endingCodePoint) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static char[] peek(PushbackReader reader, int len) throws IOException {
		char[] b = new char[len];
		reader.read(b, 0, len);
		reader.unread(b);
		return b;
	}
}
