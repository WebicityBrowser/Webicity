package com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp;

import java.io.IOException;

import com.github.webicitybrowser.ecmaspiral.parser.tokens.Token.TokenMeta;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.WhitespaceToken;

public final class WhitespaceTokenizer {
	
	private WhitespaceTokenizer() {}

	public static WhitespaceToken consumeWhitespaceToken(TokenizerStream tokenizerStream) throws IOException {
		TokenMeta meta = tokenizerStream.meta();

		StringBuilder whitespace = new StringBuilder();
		while (isWhitespace(tokenizerStream.peek())) {
			whitespace.appendCodePoint(tokenizerStream.read());
		}

		return new WhitespaceToken(whitespace.toString(), meta);
	}

	public static boolean isWhitespace(int ch) {
		switch (ch) {
		case ' ':
		case '\t':
		case '\u000b':
		case '\f':
		case '\ufeff':
		case '\u00A0':
			return true;
		default:
			return false;
		}
	}

}
