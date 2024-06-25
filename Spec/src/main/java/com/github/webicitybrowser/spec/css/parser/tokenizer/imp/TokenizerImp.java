package com.github.webicitybrowser.spec.css.parser.tokenizer.imp;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.parser.tokenizer.CSSTokenizer;
import com.github.webicitybrowser.spec.css.parser.tokens.CDOToken;
import com.github.webicitybrowser.spec.css.parser.tokens.ColonToken;
import com.github.webicitybrowser.spec.css.parser.tokens.CommaToken;
import com.github.webicitybrowser.spec.css.parser.tokens.EOFToken;
import com.github.webicitybrowser.spec.css.parser.tokens.LCBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.LParenToken;
import com.github.webicitybrowser.spec.css.parser.tokens.LSBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RCBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RParenToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RSBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.SemicolonToken;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;

public class TokenizerImp implements CSSTokenizer {
	
	@Override
	public Token[] tokenize(Reader input) throws IOException {
		ReaderHandle reader = new ReaderHandle(new PushbackReader(input, 4));
		
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
	
	private Token consumeAToken(ReaderHandle reader) throws IOException {
		consumeComments(reader);
		
		int ch = reader.read();
		switch (ch) {
		case '\n':
		case '\t':
		case ' ':
			consumeWhitespace(reader);
			return new WhitespaceToken() {};
		case '"':
		case '\'':
			return StringTokenizer.consumeString(reader, ch);
		case '#':
			return IdentTokenizer.consumeHashSign(reader);
		case '(':
			return new LParenToken() {};
		case ')':
			return new RParenToken() {};
		case '+':
			return NumberTokenizer.consumePlusSign(reader);
		case ',':
			return new CommaToken() {};
		case '-':
			return NumberTokenizer.consumeMinusSign(reader);
		case '.':
			return NumberTokenizer.consumeFullStopSign(reader);
		case ':':
			return new ColonToken() {};
		case ';':
			return new SemicolonToken() {};
		case '<':
			return consumeLessThanSign(reader);
		case '@':
			return IdentTokenizer.consumeCommercialAtSign(reader);
		case '[':
			return new LSBracketToken() {};
		case '\\':
			return IdentTokenizer.consumeReverseSolidusSign(reader);
		case ']':
			return new RSBracketToken() {};
		case '{':
			return new LCBracketToken() {};
		case '}':
			return new RCBracketToken() {};
		case -1:
			return new EOFToken() {};
		default:
			if (ASCIIUtil.isASCIIDigit(ch)) {
				reader.unread(ch);
				return NumberTokenizer.consumeANumericToken(reader);
			}
			if (IdentTokenizer.isIdentStartCodePoint(ch)) {
				reader.unread(ch);
				return IdentTokenizer.consumeAnIdentLikeToken(reader);
			}
			return SharedTokenizer.createDelimToken(ch);
		}
	}

	private void consumeComments(ReaderHandle reader) throws IOException {
		int ch1 = reader.read();
		int ch2 = reader.peek();
		reader.unread(ch1);
		
		if (ch1 == '/' && ch2 == '*') {
			reader.read();
			reader.read();
			ch1 = reader.read();
			ch2 = reader.read();
			while (ch2 != -1 && !(ch1 == '*' && ch2 == '/')) {
				ch1 = ch2;
				ch2 = reader.read();
			}
		}
	}

	private void consumeWhitespace(ReaderHandle reader) throws IOException {
		while (true) {
			int ch = reader.read();
			if (ch == -1) {
				break;
			} else if (ch != ' ' && ch != '\t' && ch != '\n') {
				reader.unread(ch);
				break;
			}
		}
	}
	
	private Token consumeLessThanSign(ReaderHandle reader) throws IOException {
		int ch1 = reader.read();
		int ch2 = reader.read();
		int ch3 = reader.read();
		
		if (ch1 == '!' && ch2 == '-' && ch3 == '-') {
			return new CDOToken() {};
		}
		
		reader.unread(ch3);
		reader.unread(ch2);
		reader.unread(ch1);
		
		return SharedTokenizer.createDelimToken('<');
	}
	
}
