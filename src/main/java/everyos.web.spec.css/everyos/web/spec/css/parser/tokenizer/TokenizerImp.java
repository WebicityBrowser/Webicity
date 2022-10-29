package everyos.web.spec.css.parser.tokenizer;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import everyos.web.spec.css.parser.tokens.BadStringToken;
import everyos.web.spec.css.parser.tokens.DelimToken;
import everyos.web.spec.css.parser.tokens.EOFToken;
import everyos.web.spec.css.parser.tokens.HashToken;
import everyos.web.spec.css.parser.tokens.HashToken.TypeFlag;
import everyos.web.spec.css.parser.tokens.LParenToken;
import everyos.web.spec.css.parser.tokens.RParenToken;
import everyos.web.spec.css.parser.tokens.StringToken;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.parser.tokens.WhitespaceToken;

public class TokenizerImp implements Tokenizer {

	private static final int MAX_CODE_POINT = 0x10FFFF;
	
	@Override
	public Token[] tokenize(Reader input) throws IOException {
		PushbackReader reader = new PushbackReader(input, 4);
		
		List<Token> tokens = new ArrayList<>();
		while (true) {
			Token token = consumeAToken(reader);
			tokens.add(token);
			
			if (token instanceof EOFToken) {
				break;
			}
		}
		
		return tokens.toArray(new Token[tokens.size()]);
	}
	
	private Token consumeAToken(PushbackReader reader) throws IOException {
		int ch = read(reader);
		switch (ch) {
		case '\n':
		case '\t':
		case ' ':
			consumeWhitespace(reader);
			return new WhitespaceToken() {};
		case '"':
		case '\'':
			return consumeString(reader, ch);
		case '#':
			return consumeHashSign(reader);
		case '(':
			return new LParenToken() {};
		case ')':
			return new RParenToken() {};
		case -1:
			return new EOFToken() {};
		default:
			return null;
		}
	}

	private void consumeWhitespace(PushbackReader reader) throws IOException {
		while (true) {
			int ch = read(reader);
			if (ch == -1) {
				break;
			} else if (ch != ' ' && ch != '\t' && ch != '\n') {
				unread(reader, ch);
				break;
			}
		}
	}
	
	private Token consumeString(PushbackReader reader, int endingCodePoint) throws IOException {
		StringBuilder value = new StringBuilder();
		
		while (true) {
			int ch = read(reader);
			switch(ch) {
			case -1:
				// TODO: Parse error
				return createStringToken(value.toString());
			case '\n':
				// TODO: Parse error
				unread(reader, ch);
				return new BadStringToken() {};
			case '\\':
				{
					int ch2 = read(reader);
					if (ch2 == -1 || ch2 == '\n') {
						break;
					}
					unread(reader, ch2);
					value.appendCodePoint(consumeAnEscapedCodePoint(reader));
				}
				break;
			default:
				if (ch == endingCodePoint) {
					return createStringToken(value.toString());
				}
				value.appendCodePoint(ch);
			}
		}
	}
	
	private Token consumeHashSign(PushbackReader reader) throws IOException {
		if (isIdentCodePoint(peek(reader)) || isValidEscapeSequence(reader)) {
			return consumeHashToken(reader);
		}
		
		return createDelimToken('#');
	}

	private Token consumeHashToken(PushbackReader reader) throws IOException {
		TypeFlag flag = wouldStartAnIdentSequence(reader) ?
			TypeFlag.ID :
			TypeFlag.UNRESTRICTED;
		String value = consumeAnIdentSequence(reader);
		
		return createHashToken(value, flag);
	}

	private String consumeAnIdentSequence(PushbackReader reader) throws IOException {
		StringBuilder result = new StringBuilder();
		while (true) {
			int ch = peek(reader);
			if (isIdentCodePoint(ch)) {
				read(reader);
				result.appendCodePoint(ch);
			} else if (isValidEscapeSequence(reader)) {
				read(reader);
				result.appendCodePoint(consumeAnEscapedCodePoint(reader));
			} else {
				return result.toString();
			}
		}
	}

	private int consumeAnEscapedCodePoint(PushbackReader reader) throws IOException {
		int ch = read(reader);
		if (isHexDigit(ch)) {
			unread(reader, ch);
			return consumeHexEscapedCodePoint(reader);
		} else if (ch == -1) {
			return '\uFFFD';
		} else {
			return ch;
		}
	}
	
	private int consumeHexEscapedCodePoint(PushbackReader reader) throws IOException {
		int total = 0;
		for (int i = 0; i < 6; i++) {
			int ch = read(reader);
			if (!isHexDigit(ch)) {
				unread(reader, ch);
				break;
			}
			total <<= 4;
			total += fromHexCharacter(ch);
		}
		int ch = read(reader);
		if (!isWhitespace(ch)) {
			unread(reader, ch);
		}
		
		if (total == 0 || total > MAX_CODE_POINT || isSurrogate(total)) {
			return '\uFFFD';
		}
		return total;
	}
	
	private StringToken createStringToken(String value) {
		return () -> value;
	}
	
	private DelimToken createDelimToken(char ch) {
		return () -> ch;
	}
	
	private HashToken createHashToken(String value, TypeFlag flag) {
		return new HashToken() {
			
			@Override
			public String getValue() {
				return value;
			}
			
			@Override
			public TypeFlag getTypeFlag() {
				return flag;
			}
			
		};
	}
	
	private int fromHexCharacter(int ch) {
		assert isHexDigit(ch) : "Character must be hex digit!";
		if (ch >= '0' && ch <= '9') {
			return ch - '0';
		} else if (ch >= 'a' && ch <= 'f') {
			return 10 + ch - 'a';
		} else {
			return 10 + ch - 'A';
		}
	}
	
	private boolean wouldStartAnIdentSequence(PushbackReader reader) throws IOException {
		int ch = peek(reader);
		
		switch(ch) {
		case '-':
			read(reader);
			if (
				isIdentStartCodePoint(peek(reader)) ||
				peek(reader) == '-' ||
				isValidEscapeSequence(reader)
			) {
				unread(reader, ch);
				return true;
			}
		case '/':
			return isValidEscapeSequence(reader);
		default:
			return isIdentStartCodePoint(ch);
		}
	}
	
	private boolean isValidEscapeSequence(PushbackReader reader) throws IOException {
		int ch1 = read(reader);
		int ch2 = read(reader);
		unread(reader, ch2);
		unread(reader, ch1);
		
		return
			ch1 == '\\' &&
			ch2 != '\n';
	}

	private boolean isSurrogate(int total) {
		return (total >= '\uD800' && total <= '\uDFFF');
	}
	
	private boolean isWhitespace(int ch) {
		return
			ch == '\n' ||
			ch == '\t' ||
			ch == ' ';
	}
	
	private boolean isDigit(int ch) {
		return
			ch >= '0' &&
			ch <= '9';
	}
	
	private boolean isLetter(int ch) {
		return
			(ch >= 'a' && ch <= 'z') ||
			(ch >= 'A' && ch <= 'Z');
	}
	
	private boolean isHexDigit(int ch) {
		return
			isDigit(ch) ||
			(ch >= 'a' && ch <= 'f') ||
			(ch >= 'A' && ch <= 'F');
	}
	
	private boolean isIdentCodePoint(int ch) {
		return
			isIdentStartCodePoint(ch) ||
			isDigit(ch) ||
			ch == '-';
	}

	private boolean isIdentStartCodePoint(int ch) {
		return
			isLetter(ch) ||
			(ch >= 0x80 && ch <= MAX_CODE_POINT) ||
			ch == '_';
	}
	
	private int read(PushbackReader reader) throws IOException {
		int ch = reader.read();
		switch(ch) {
		case '\r':
			{
				int ch2 = reader.read();
				if (ch2 != '\n') {
					unread(reader, ch2);
				}
			}
			return '\n';
		case '\f':
			return '\n';
		default:
			return ch;
		}
	}
	
	private int peek(PushbackReader reader) throws IOException {
		int result = read(reader);
		unread(reader, result);
		
		return result;
	}

	private void unread(PushbackReader reader, int ch) throws IOException {
		if (ch != -1) {
			reader.unread(ch);
		}
	}
	
}
