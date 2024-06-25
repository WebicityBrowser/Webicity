package com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp;

import java.io.IOException;

import com.github.webicitybrowser.ecmaspiral.parser.tokens.NewlineToken;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.Token.TokenMeta;

public class NewlineTokenizer {

	public static NewlineToken consumeNewlineToken(TokenizerStream tokenizerStream) throws IOException {
		TokenMeta meta = tokenizerStream.meta();

		StringBuilder whitespace = new StringBuilder();
		while (isNewline(tokenizerStream.peek())) {
			whitespace.appendCodePoint(tokenizerStream.read());
		}

		return new NewlineToken(whitespace.toString(), meta);
	}

	public static boolean isNewline(int ch) {
		switch (ch) {
		case '\n':
		case '\r':
		case '\u2028':
		case '\u2029':
			return true;
		default:
			return false;
		}
	}

}
