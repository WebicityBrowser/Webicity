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
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
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

		if (ASCIIUtil.isASCIIDigit(ch)) {
			reader.unread(ch);
			return NumberTokenizer.consumeANumericToken(reader);
		}
		if (IdentTokenizer.isIdentStartCodePoint(ch)) {
			reader.unread(ch);
			return IdentTokenizer.consumeAnIdentLikeToken(reader);
		}

		return switch (ch) {
			case '\n', '\t', ' ' -> consumeWhitespace(reader);
			case '"', '\'' -> StringTokenizer.consumeString(reader, ch);
			case '#' -> IdentTokenizer.consumeHashSign(reader);
			case '(' -> new LParenToken();
			case ')' -> new RParenToken();
			case '+' ->NumberTokenizer.consumePlusSign(reader);
			case ',' -> new CommaToken();
			case '-' -> NumberTokenizer.consumeMinusSign(reader);
			case '.' -> NumberTokenizer.consumeFullStopSign(reader);
			case ':' -> new ColonToken();
			case ';' -> new SemicolonToken();
			case '<' -> consumeLessThanSign(reader);
			case '@' -> IdentTokenizer.consumeCommercialAtSign(reader);
			case '[' -> new LSBracketToken();
			case '\\' -> IdentTokenizer.consumeReverseSolidusSign(reader);
			case ']' -> new RSBracketToken();
			case '{' -> new LCBracketToken();
			case '}' -> new RCBracketToken();
			case -1 -> new EOFToken();
			default -> new DelimToken(ch);
		};
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

	private WhitespaceToken consumeWhitespace(ReaderHandle reader) throws IOException {
		while (true) {
			int ch = reader.read();
			if (ch == -1) {
				break;
			} else if (ch != ' ' && ch != '\t' && ch != '\n') {
				reader.unread(ch);
				break;
			}
		}

		return new WhitespaceToken();
	}
	
	private Token consumeLessThanSign(ReaderHandle reader) throws IOException {
		int ch1 = reader.read();
		int ch2 = reader.read();
		int ch3 = reader.read();
		
		if (ch1 == '!' && ch2 == '-' && ch3 == '-') {
			return new CDOToken();
		}
		
		reader.unread(ch3);
		reader.unread(ch2);
		reader.unread(ch1);
		
		return new DelimToken('<');
	}
	
}
