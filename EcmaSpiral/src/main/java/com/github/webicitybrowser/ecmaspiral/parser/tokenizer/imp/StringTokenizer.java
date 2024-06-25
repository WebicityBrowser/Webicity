package com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp;

import java.io.IOException;

import com.github.webicitybrowser.ecmaspiral.parser.exception.ParseException;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.StringToken;

public final class StringTokenizer {

	private StringTokenizer() {}

	public static StringToken consumeStringToken(TokenizerStream stream) throws IOException, ParseException {
		int firstChar = stream.read();
		if (firstChar != '"' && firstChar != '\'') {
			throw new IllegalStateException("Tokenize Error: Unexpected character: " + firstChar);
		}
		
		StringBuilder valueBuilder = new StringBuilder();
		while (stream.peek() != firstChar) {
			int ch = stream.read();
			if (ch == -1) {
				throw new ParseException("Tokenize Error: Unexpected end of input", stream.meta());
			}
			valueBuilder.appendCodePoint(ch);
		}

		stream.read();

		return new StringToken(valueBuilder.toString(), stream.meta());
	}

}
