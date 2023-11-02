package com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp;

import java.io.IOException;

import com.github.webicitybrowser.ecmaspiral.parser.tokens.NumericToken;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.Token;

public final class NumericTokenizer {
	
	private NumericTokenizer() {}

	public static Token consumeNumericToken(TokenizerStream stream) throws IOException {
		Number numberValue = consumeDecimalLiteral(stream);
		return new NumericToken(numberValue, stream.meta());
	}

	private static Number consumeDecimalLiteral(TokenizerStream stream) throws IOException {
		Number numberValue = stream.peek() != '.' ? consumeDecimalIntegerLiteral(stream) : 0;
		if (stream.peek() == '.') {
			stream.read();
			Number fractionPart = consumeDecimalDigits(stream, true);
			System.out.println("numberValue: " + numberValue);
			System.out.println("fractionPart: " + fractionPart);

			numberValue = numberValue.intValue() + fractionPart.doubleValue();
		}

		return numberValue;
	}

	private static Number consumeDecimalIntegerLiteral(TokenizerStream stream) throws IOException {
		return consumeDecimalDigits(stream, false);
	}

	private static Number consumeDecimalDigits(TokenizerStream stream, boolean isAfterDot) throws IOException {
		int intValue = 0;
		int i = 0;
		while (isDecimalDigit(stream.peek())) {
			intValue = intValue * 10 + stream.read() - '0';
			i++;
		}

		if (isAfterDot && i == 0) {
			throw new IllegalStateException("Expected decimal digit");
		}

		if (isAfterDot) {
			return intValue / Math.pow(10, i);
		} else {
			return intValue;
		}
	}

	public static boolean isNumericStart(int ch) {
		return isDecimalDigit(ch) || ch == '.';
	}

	private static boolean isDecimalDigit(int ch) {
		return ch > '0' && ch < '9';
	}

}
