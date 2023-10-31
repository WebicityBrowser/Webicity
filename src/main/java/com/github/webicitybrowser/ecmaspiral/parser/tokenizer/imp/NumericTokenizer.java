package com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp;

import java.io.IOException;

import com.github.webicitybrowser.ecmaspiral.parser.tokens.NumericToken;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.Token;

public final class NumericTokenizer {
	
	private NumericTokenizer() {}

	public static Token consumeNumericToken(TokenizerStream stream) throws IOException {
		return consumeDecimalLiteral(stream);
	}

	private static Token consumeDecimalLiteral(TokenizerStream stream) throws IOException {
		return consumeDecimalIntegerLiteral(stream);
	}

	private static Token consumeDecimalIntegerLiteral(TokenizerStream stream) throws IOException {
		StringBuilder value = new StringBuilder();
		while (isDecimalDigit(stream.peek())) {
			value.appendCodePoint(stream.read());
		}

		Number numberValue = Integer.parseInt(value.toString());

		return new NumericToken(numberValue, stream.meta());
	}

	public static boolean isNumericStart(int ch) {
		return isDecimalDigit(ch);
	}

	private static boolean isDecimalDigit(int ch) {
		return ch > '0' && ch < '9';
	}

}
