package everyos.browser.jcss.parser;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

public class JCSSTokenizer {
	public CSSToken[] createFromString(String input) {
		//TODO: Switch to a proper input stream
		input = input
			.replace("/r/n", "/n")
			.replace("/f",   "/n")
			.replace("/r",   "/n");
		
		try {
			return createFromStream(new StringReader(input));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
		int ch = reader.read();
		
		if (Character.isWhitespace(ch)) {
			while (Character.isWhitespace(peek(reader, 1)[0])) {
				reader.read();
			};
			return new WhitespaceToken();
		} else if (ch=='"') {
			return consumeAStringToken(reader, '"');
		} else if (ch=='#') {
			if (isANameCodepoint(peek(reader, 1)[0]) || isAValidEscape(peek(reader, 1)[0], peek(reader, 2)[1])) {	
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
			if (isAValidEscape(ch, peek(reader, 1)[0])) {
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
		} else if (isANameStartCodepoint(ch)) { //TODO: Skipped a few
			reader.unread(ch);
			return consumeAnIdentLikeToken(reader);
		} else if (ch == -1) {
			return new EOFToken();
		} else {
			return new DelimToken(ch);
		}
	}

	private static boolean startsWithAnIdentifier_(PushbackReader reader) throws IOException {
		int ch = reader.read();
		boolean result = startsWithAnIdentifier(ch, reader);
		reader.unread(ch);
		return result;
	}

	private static CSSToken consumeAnIdentLikeToken(PushbackReader reader) throws IOException {
		String string = consumeAName(reader);
		
		if (string.equalsIgnoreCase("url") && peek(reader, 1)[0] == '(') {
			reader.read();
			while (Character.isWhitespace(peek(reader, 1)[0]) && Character.isWhitespace(peek(reader, 2)[1])) {
				reader.read();
			}
			
			int i = 0;
			if (Character.isWhitespace(peek(reader, 1)[0])) {
				i++;
			}
			if (peek(reader, i+1)[i] == '"' || peek(reader, i+1)[i] == '\'') {
				return new FunctionToken(string);
			} else {
				return consumeAURLToken(reader);
			}
			
			
		} else if (peek(reader, 1)[0] == '(') {
			reader.read();
			return new FunctionToken(string);
		} else {
			return new IdentToken(string);
		}
	}

	private static CSSToken consumeAURLToken(PushbackReader reader) {
		// TODO Auto-generated method stub
		return null;
	}

	private static CSSToken consumeANumericToken(PushbackReader reader) throws IOException {
		Tuple<Number, String> number = consumeANumber(reader);
		if (startsWithAnIdentifier_(reader)) {
			return new DimensionToken(number.getT1(), number.getT2(), consumeAName(reader));
		} else if (peek(reader, 1)[0] == '%') {
			reader.read();
			return new PercentageToken(number.getT1());
		} else {
			return new NumberToken(number.getT1(), number.getT2());
		}
	}

	private static Tuple<Number, String> consumeANumber(PushbackReader reader) throws IOException {
		String type = "integer"; //TODO: For performance, this should probably be an enum or int.
		StringBuilder repr = new StringBuilder();
		
		if (peek(reader, 1)[0] == '+' || peek(reader, 1)[0] == '-') {
			repr.appendCodePoint(reader.read());
		}
		
		//TODO: isDigit includes digits which we don't want
		while (Character.isDigit(peek(reader, 1)[0])) {
			repr.appendCodePoint(reader.read());
		}
		
		if (peek(reader, 1)[0] == '.' && Character.isDigit(peek(reader, 2)[1])) {
			reader.read();
			type = "number";
			while (Character.isDigit(peek(reader, 1)[0])) {
				repr.appendCodePoint(reader.read());
			}
		}
		
		char[] peeked = peek(reader, 3);
		if (
			(peeked[0] == 'e' || peeked[0] == 'E') &&
			(((peeked[1] == '-' || peeked[1] == '+') && Character.isDigit(peeked[2])) || 
			Character.isDigit(peeked[1]))) {
			
			repr.appendCodePoint(reader.read());
			int ch = reader.read();
			repr.appendCodePoint(ch);
			if (ch == '+' || ch == '-') {
				repr.appendCodePoint(reader.read());
			}
			
			type = "number";
			while (Character.isDigit(peek(reader, 1)[0])) {
				repr.appendCodePoint(reader.read());
			}
		}
		
		
		Number value;
		if (type.equals("integer")) {
			value = Integer.valueOf(repr.toString());
		} else {
			value = Double.valueOf(repr.toString());
		}
		
		return new Tuple<Number, String>(value, type);
	}

	private static boolean startsWithANumber(int ch, PushbackReader reader) throws IOException {
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
		switch(ch) {
			case '-':
				return
					peek(reader, 1)[0] == '-' ||
					isAValidEscape(peek(reader, 1)[0], peek(reader, 2)[1]);	
			case '\\':
				return isAValidEscape(ch, peek(reader, 1)[0]);
			default:
				return isANameStartCodepoint(ch);
		}
	}

	private static String consumeAName(PushbackReader reader) throws IOException {
		StringBuilder result = new StringBuilder();
		while (true) {
			int ch = reader.read();
			if (isANameCodepoint(ch)) {
				result.appendCodePoint(ch);
			} else if (isAValidEscape(ch, peek(reader, 1)[0])) {
				result.appendCodePoint(consumeAnEscapedCodepoint(reader));
			} else {
				reader.unread(ch);
				return result.toString();
			}
		}
	}

	private static int consumeAnEscapedCodepoint(PushbackReader reader) throws IOException {
		int ch = reader.read();
		if (isHexDigit(ch)) {
			StringBuilder hex = new StringBuilder();
			hex.appendCodePoint(ch);
			for (int i = 0; i < 5; i++) {
				if (!isHexDigit(peek(reader, 1)[0])) {
					break;
				}
				hex.appendCodePoint(reader.read());
			}
			
			if (Character.isWhitespace(peek(reader, 1)[0])) {
				reader.read();
			}
			int codepoint = Integer.parseInt(hex.toString(), 16);
			if (codepoint == 0 || codepoint > 0x10FFFF || (codepoint < 256 && Character.isSurrogate((char) codepoint))) {
				return '\uFFFD';
			}
			return codepoint;
		} else if (ch == -1) {
			return '\uFFFD';
		} else {
			return ch;
		}
	}

	private static boolean isHexDigit(int ch) {
		return
			(ch >= '0' && ch <= '9') ||
			(ch >= 'a' && ch <= 'f') ||
			(ch >= 'A' && ch <= 'F');
	}

	private static boolean isAValidEscape(int ch1, int ch2) {
		return ch1=='\\' && ch2!='\n';
	}

	private static boolean isANameCodepoint(int ch) {
		return isANameStartCodepoint(ch) || Character.isDigit(ch) || ch=='-';
	}

	private static boolean isANameStartCodepoint(int ch) {
		return Character.isLetter(ch) || ch>='\u0080' || ch=='_';
	}

	private static CSSToken consumeAStringToken(PushbackReader reader, int endingCodePoint) throws IOException {
		StringBuilder value = new StringBuilder();
		
		while (true) {
			int ch = reader.read();
			if (ch == endingCodePoint || ch == -1) {
				return new StringToken(value.toString());
			}
			switch(ch) {
				case '\n':
					reader.unread(ch);
					return new BadStringToken();
				case '\\':
					if (peek(reader, 1)[0] == -1) {
						
					} else if (peek(reader, 1)[0] == '\n') {
						reader.read();
					} else {
						value.appendCodePoint(consumeAnEscapedCodepoint(reader));
					}
				default:
					value.appendCodePoint(endingCodePoint);
			}
		}
	}
	
	//TODO: This should return an int[]
	private static char[] peek(PushbackReader reader, int len) throws IOException {
		char[] b = new char[len];
		reader.read(b, 0, len);
		reader.unread(b);
		return b;
	}
}
