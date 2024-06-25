package com.github.webicitybrowser.spec.css.parser.tokenizer.imp;

import java.io.IOException;

import com.github.webicitybrowser.spec.css.parser.tokens.BadURLToken;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.parser.tokens.URLToken;

public final class URLTokenizer {

	private URLTokenizer() {}
	
	public static Token consumeAURLToken(ReaderHandle reader) throws IOException {
		StringBuilder url = new StringBuilder();
		while (SharedTokenizer.isWhitespace(reader.peek())) {
			reader.read();
		}
		while (true) {
			int ch = reader.read();
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
				if (SharedTokenizer.isValidEscapeSequence(ch, reader)) {
					int escapedCodePoint = SharedTokenizer.consumeAnEscapedCodePoint(reader);
					url.appendCodePoint(escapedCodePoint);
					continue;
				} else {
					// TODO: Parse Error
					consumeTheRemnantsOfABadUrl(reader);
					return new BadURLToken() {};
				}
			default:
				if (SharedTokenizer.isWhitespace(ch)) {
					while (SharedTokenizer.isWhitespace(reader.peek())) {
						reader.read();
					}
					if (reader.peek() == ')') {
						reader.read();
						return createURLToken(url.toString());
					}
					if (reader.peek() == -1) {
						// TODO: Parse Error
						reader.read();
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
	
	private static void consumeTheRemnantsOfABadUrl(ReaderHandle reader) throws IOException {
		while (true) {
			int ch = reader.read();
			if (ch == ')' || ch == -1) {
				return;
			} else if (SharedTokenizer.isValidEscapeSequence(ch, reader)) {
				SharedTokenizer.consumeAnEscapedCodePoint(reader);
			}
		}
	}
	
	private static URLToken createURLToken(String value) {
		return () -> value;
	}
	
}
