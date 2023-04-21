package com.github.webicitybrowser.spec.css.parser.tokenizer.imp;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.parser.tokenizer.CSSTokenizer;
import com.github.webicitybrowser.spec.css.parser.tokens.AtKeywordToken;
import com.github.webicitybrowser.spec.css.parser.tokens.BadStringToken;
import com.github.webicitybrowser.spec.css.parser.tokens.BadURLToken;
import com.github.webicitybrowser.spec.css.parser.tokens.CDCToken;
import com.github.webicitybrowser.spec.css.parser.tokens.CDOToken;
import com.github.webicitybrowser.spec.css.parser.tokens.ColonToken;
import com.github.webicitybrowser.spec.css.parser.tokens.CommaToken;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.DimensionToken;
import com.github.webicitybrowser.spec.css.parser.tokens.EOFToken;
import com.github.webicitybrowser.spec.css.parser.tokens.FunctionToken;
import com.github.webicitybrowser.spec.css.parser.tokens.HashToken;
import com.github.webicitybrowser.spec.css.parser.tokens.HashToken.HashTypeFlag;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.LCBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.LParenToken;
import com.github.webicitybrowser.spec.css.parser.tokens.LSBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberTypeFlag;
import com.github.webicitybrowser.spec.css.parser.tokens.PercentageToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RCBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RParenToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RSBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.SemicolonToken;
import com.github.webicitybrowser.spec.css.parser.tokens.StringToken;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.parser.tokens.URLToken;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;

public class TokenizerImp implements CSSTokenizer {

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
		consumeComments(reader);
		
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
		case '+':
			return consumePlusSign(reader);
		case ',':
			return new CommaToken() {};
		case '-':
			return consumeMinusSign(reader);
		case '.':
			return consumeFullStopSign(reader);
		case ':':
			return new ColonToken() {};
		case ';':
			return new SemicolonToken() {};
		case '<':
			return consumeLessThanSign(reader);
		case '@':
			return consumeCommercialAtSign(reader);
		case '[':
			return new LSBracketToken() {};
		case '\\':
			return consumeReverseSolidusSign(reader);
		case ']':
			return new RSBracketToken() {};
		case '{':
			return new LCBracketToken() {};
		case '}':
			return new RCBracketToken() {};
		case -1:
			return new EOFToken() {};
		default:
			if (isDigit(ch)) {
				unread(reader, ch);
				return consumeANumericToken(reader);
			}
			if (isIdentStartCodePoint(ch)) {
				unread(reader, ch);
				return consumeAnIdentLikeToken(reader);
			}
			return createDelimToken(ch);
		}
	}

	private void consumeComments(PushbackReader reader) throws IOException {
		int ch1 = read(reader);
		int ch2 = peek(reader);
		unread(reader, ch1);
		
		if (ch1 == '/' && ch2 == '*') {
			read(reader);
			read(reader);
			ch1 = read(reader);
			ch2 = read(reader);
			while (ch2 != -1 && !(ch1 == '*' && ch2 == '/')) {
				ch1 = ch2;
				ch2 = read(reader);
			}
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
		if (isIdentCodePoint(peek(reader)) || wouldStartValidEscapeSequence(reader)) {
			return consumeHashToken(reader);
		}
		
		return createDelimToken('#');
	}
	
	private Token consumePlusSign(PushbackReader reader) throws IOException {
		if (isDigit(peek(reader))) {
			unread(reader, '+');
			return consumeANumericToken(reader);
		} else {
			return createDelimToken('+');
		}
	}
	
	private Token consumeMinusSign(PushbackReader reader) throws IOException {
		if (isDigit(peek(reader))) {
			unread(reader, '-');
			return consumeANumericToken(reader);
			//TODO
		}
		
		{
			int ch1 = read(reader);
			int ch2 = read(reader);
			if (ch1 == '-' && ch2 == '>') {
				return new CDCToken() {};
			}
			unread(reader, ch2);
			unread(reader, ch1);
		}
		
		if (isValidIdentSequence('-', reader)) {
			unread(reader, '-');
			return consumeAnIdentLikeToken(reader);
		}
		
		return createDelimToken('-');
	}
	
	private Token consumeFullStopSign(PushbackReader reader) throws IOException {
		if (wouldStartANumber('.', reader)) {
			unread(reader, '.');
			return consumeANumericToken(reader);
		}
		return createDelimToken('.');
	}
	
	private Token consumeLessThanSign(PushbackReader reader) throws IOException {
		int ch1 = read(reader);
		int ch2 = read(reader);
		int ch3 = read(reader);
		
		if (ch1 == '!' && ch2 == '-' && ch3 == '-') {
			return new CDOToken() {};
		}
		
		unread(reader, ch3);
		unread(reader, ch2);
		unread(reader, ch1);
		
		return createDelimToken('<');
	}
	
	private Token consumeCommercialAtSign(PushbackReader reader) throws IOException {
		if (wouldStartAnIdentSequence(reader)) {
			String value = consumeAnIdentSequence(reader);
			return createAtKeywordToken(value);
		}
		
		return createDelimToken('@');
	}
	
	private Token consumeReverseSolidusSign(PushbackReader reader) throws IOException {
		if (isValidEscapeSequence('\\', reader)) {
			unread(reader, '\\');
			return consumeAnIdentLikeToken(reader);
		}
		
		//TODO: Parse error
		return createDelimToken('\\');
	}

	private Token consumeHashToken(PushbackReader reader) throws IOException {
		HashTypeFlag flag = wouldStartAnIdentSequence(reader) ?
			HashTypeFlag.ID :
			HashTypeFlag.UNRESTRICTED;
		String value = consumeAnIdentSequence(reader);
		
		return createHashToken(value, flag);
	}
	
	private Token consumeANumericToken(PushbackReader reader) throws IOException {
		Number number = consumeANumber(reader);
		
		if (wouldStartAnIdentSequence(reader)) {
			String unit = consumeAnIdentSequence(reader);
			return createDimensionToken(number, unit);
		}
		
		if (peek(reader) == '%') {
			read(reader);
			return createPercentageToken(number);
		}
		
		return createNumberToken(number);
	}
	
	private Token consumeAnIdentLikeToken(PushbackReader reader) throws IOException {
		String string = consumeAnIdentSequence(reader);
		
		if (string.equalsIgnoreCase("url") && peek(reader) == '(') {
			read(reader);
			while (true) {
				int ch1 = read(reader);
				int ch2 = peek(reader);
				unread(reader, ch1);
				if (isWhitespace(ch1) && isWhitespace(ch2)) {
					read(reader);
				} else {
					break;
				}
			}
			
			int ch1 = read(reader);
			int ch2 = peek(reader);
			unread(reader, ch1);
			if (
				ch1 == '"' ||
				ch1 == '\'' ||
				(isWhitespace(ch1) && ch2 == '"') ||
				(isWhitespace(ch1) && ch2 == '\'')
			) {
				return createFunctionToken(string);
			} else {
				return consumeAURLToken(reader);
			}
		}
		
		if (peek(reader) == '(') {
			read(reader);
			return createFunctionToken(string);
		}
		
		return createIdentToken(string);
	}

	private Token consumeAURLToken(PushbackReader reader) throws IOException {
		StringBuilder url = new StringBuilder();
		while (isWhitespace(peek(reader))) {
			read(reader);
		}
		while (true) {
			int ch = read(reader);
			switch(ch) {
			case ')':
				return createURLToken(url.toString());
			case -1:
				// TODO: Parse Error
				return createURLToken(url.toString());
			case '"':
			case '\'':
			case '(':
				// TODO: Parse Error
				consumeTheRemnantsOfABadUrl(reader);
				return new BadURLToken() {};
			case '\\':
				if (isValidEscapeSequence(ch, reader)) {
					int escapedCodePoint = consumeAnEscapedCodePoint(reader);
					url.appendCodePoint(escapedCodePoint);
					continue;
				} else {
					// TODO: Parse Error
					consumeTheRemnantsOfABadUrl(reader);
					return new BadURLToken() {};
				}
			default:
				if (isWhitespace(ch)) {
					while (isWhitespace(peek(reader))) {
						read(reader);
					}
					if (peek(reader) == ')') {
						read(reader);
						return createURLToken(url.toString());
					}
					if (peek(reader) == -1) {
						// TODO: Parse Error
						read(reader);
						return createURLToken(url.toString());
					} else {
						// TODO: (Implied) Parse Error
						// The spec does not actually define this as a parse error
						consumeTheRemnantsOfABadUrl(reader);
						return new BadURLToken() {};
					}
				}
				url.appendCodePoint(ch);
			}	
		}
	}

	private void consumeTheRemnantsOfABadUrl(PushbackReader reader) throws IOException {
		while (true) {
			int ch = read(reader);
			if (ch == ')' || ch == -1) {
				return;
			} else if (isValidEscapeSequence(ch, reader)) {
				consumeAnEscapedCodePoint(reader);
			}
		}
	}

	private Number consumeANumber(PushbackReader reader) throws IOException {
		NumberTypeFlag type = NumberTypeFlag.INTEGER;
		StringBuilder repr = new StringBuilder();
		if (peek(reader) == '+' || peek(reader) == '-') {
			repr.appendCodePoint(read(reader));
		}
		
		while (isDigit(peek(reader))) {
			repr.appendCodePoint(read(reader));
		}
		
		{
			int ch1 = read(reader);
			int ch2 = read(reader);
			if (ch1 == '.' && isDigit(ch2)) {
				repr.appendCodePoint(ch1);
				repr.appendCodePoint(ch2);
				type = NumberTypeFlag.NUMBER;
				while (isDigit(peek(reader))) {
					repr.appendCodePoint(read(reader));
				}
			} else {
				unread(reader, ch2);
				unread(reader, ch1);
			}
		}
		
		{
			int ch1 = read(reader);
			int ch2 = read(reader);
			int ch3 = read(reader);
			if (
				(ch1 == 'e' || ch1 == 'E') &&
				(
					((ch2 == '+' || ch2 == '-') && isDigit(ch3)) ||
					isDigit(ch2)
				)
			) {
				if (!(ch2 == '+' || ch2 == '-')) {
					unread(reader, ch3);
					ch3 = ch2;
					ch2 = '+';
				}
				repr.appendCodePoint(ch1);
				repr.appendCodePoint(ch2);
				repr.appendCodePoint(ch3);
				type = NumberTypeFlag.NUMBER;
				while (isDigit(peek(reader))) {
					repr.appendCodePoint(read(reader));
				}
			} else {
				unread(reader, ch3);
				unread(reader, ch2);
				unread(reader, ch1);
			}
		}
		
		if (type == NumberTypeFlag.INTEGER) {
			return Integer.valueOf(repr.toString());
		} else {
			return Double.parseDouble(repr.toString());
		}
	}

	private String consumeAnIdentSequence(PushbackReader reader) throws IOException {
		StringBuilder result = new StringBuilder();
		while (true) {
			int ch = peek(reader);
			if (isIdentCodePoint(ch)) {
				read(reader);
				result.appendCodePoint(ch);
			} else if (isValidEscapeSequence(ch, reader)) {
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
	
	private DelimToken createDelimToken(int ch) {
		return () -> ch;
	}
	
	private IdentToken createIdentToken(String value) {
		return () -> value;
	}
	
	private FunctionToken createFunctionToken(String value) {
		return () -> value;
	}
	
	private URLToken createURLToken(String value) {
		return () -> value;
	}
	
	private AtKeywordToken createAtKeywordToken(String value) {
		return () -> value;
	}
	
	private NumberToken createNumberToken(Number number) {
		NumberTypeFlag flag = number instanceof Integer ?
			NumberTypeFlag.INTEGER :
			NumberTypeFlag.NUMBER;
		
		return new NumberToken() {
			@Override
			public Number getValue() {
				return number;
			}

			@Override
			public NumberTypeFlag getTypeFlag() {
				return flag;
			}	
		};
	}
	
	private DimensionToken createDimensionToken(Number number, String unit) {
		NumberTypeFlag flag = number instanceof Integer ?
			NumberTypeFlag.INTEGER :
			NumberTypeFlag.NUMBER;
		
		return new DimensionToken() {
			@Override
			public Number getValue() {
				return number;
			}

			@Override
			public NumberTypeFlag getTypeFlag() {
				return flag;
			}

			@Override
			public String getUnit() {
				return unit;
			}	
		};
	}
	
	private PercentageToken createPercentageToken(Number number) {
		return () -> number;
	}
	
	private HashToken createHashToken(String value, HashTypeFlag flag) {
		return new HashToken() {
			
			@Override
			public String getValue() {
				return value;
			}
			
			@Override
			public HashTypeFlag getTypeFlag() {
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
	
	private boolean wouldStartANumber(int ch1, PushbackReader reader) throws IOException {
		int ch2 = read(reader);
		int ch3 = peek(reader);
		unread(reader, ch2);
		
		switch(ch1) {
		case '+':
		case '-':
			if (isDigit(ch2)) {
				return true;
			}
			return
				ch2 == '.' &&
				isDigit(ch3);
		case '.':
			return isDigit(ch2);
		default:
			return isDigit(ch1);
		}
	}
	
	private boolean wouldStartAnIdentSequence(PushbackReader reader) throws IOException {
		int ch = read(reader);
		boolean wouldStart = isValidIdentSequence(ch, reader);
		unread(reader, ch);
		
		return wouldStart;
	}
	
	private boolean isValidIdentSequence(int ch, PushbackReader reader) throws IOException {
		switch(ch) {
		case '-':
			read(reader);
			if (
				isIdentStartCodePoint(peek(reader)) ||
				peek(reader) == '-' ||
				wouldStartValidEscapeSequence(reader)
			) {
				unread(reader, ch);
				return true;
			}
			unread(reader, ch);
			return false;
		case '/':
			return isValidEscapeSequence(ch, reader);
		default:
			return isIdentStartCodePoint(ch);
		}
	}
	
	private boolean isValidEscapeSequence(int ch1, PushbackReader reader) throws IOException {
		int ch2 = peek(reader);
		
		return
			ch1 == '\\' &&
			ch2 != '\n';
	}
	
	private boolean wouldStartValidEscapeSequence(PushbackReader reader) throws IOException {
		int ch1 = read(reader);
		int ch2 = peek(reader);
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
