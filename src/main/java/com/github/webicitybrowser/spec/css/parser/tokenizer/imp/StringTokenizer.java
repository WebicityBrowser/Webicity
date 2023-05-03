package com.github.webicitybrowser.spec.css.parser.tokenizer.imp;

import java.io.IOException;

import com.github.webicitybrowser.spec.css.parser.tokens.BadStringToken;
import com.github.webicitybrowser.spec.css.parser.tokens.StringToken;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;

public final class StringTokenizer {

	private StringTokenizer() {}

	public static Token consumeString(ReaderHandle reader, int endingCodePoint) throws IOException {
		StringBuilder value = new StringBuilder();
		
		while (true) {
			int ch = reader.read();
			switch(ch) {
			case -1:
				// TODO: Parse error
				return createStringToken(value.toString());
			case '\n':
				// TODO: Parse error
				reader.unread(ch);
				return new BadStringToken() {};
			case '\\':
				appendEscapedCodepoint(reader, value);
				break;
			default:
				if (ch == endingCodePoint) {
					return createStringToken(value.toString());
				}
				value.appendCodePoint(ch);
			}
		}
	}
	
	private static void appendEscapedCodepoint(ReaderHandle reader, StringBuilder value) throws IOException {
		int ch2 = reader.read();
		if (ch2 == -1 || ch2 == '\n') {
			return;
		}
		reader.unread(ch2);
		value.appendCodePoint(SharedTokenizer.consumeAnEscapedCodePoint(reader));
	}

	private static StringToken createStringToken(String value) {
		return () -> value;
	}
	
}
