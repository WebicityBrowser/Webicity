package com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp;

import java.io.IOException;

import com.github.webicitybrowser.ecmaspiral.parser.exception.ParseException;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.IdentifierToken;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.Token;

public final class IdentifierTokenizer {

	private IdentifierTokenizer() {}

	// TODO: Unicode escape sequences

	public static Token consumeIdentifierToken(TokenizerStream stream) throws IOException, ParseException {
		if (!isIdentifierStart(stream.peek())) {
			throw new IllegalStateException("Tokenize Error: Unexpected character: " + stream.peek());
		}

		StringBuilder nameBuilder = new StringBuilder();
		nameBuilder.appendCodePoint(stream.read());
		while (isIdentifierPart(stream.peek())) {
			nameBuilder.appendCodePoint(stream.read());
		}

		return new IdentifierToken(nameBuilder.toString(), stream.meta());
	}

	public static boolean isIdentifierStart(int ch) {
		return ch == '$' || ch == '_' || Character.isUnicodeIdentifierStart(ch);
	}

	private static boolean isIdentifierPart(int ch) {
		return ch == '$' || ch == 0x200C || ch == 0x200D || Character.isUnicodeIdentifierPart(ch);
	}

}
