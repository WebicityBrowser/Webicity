package com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp.numeric;

import java.io.IOException;

import com.github.webicitybrowser.ecmaspiral.parser.exception.ParseException;
import com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp.TokenizerStream;

public class DecimalTokenizer {
	
	public static Number consumeDecimalLiteral(TokenizerStream stream) throws IOException, ParseException {
		Number numberValue = stream.peek() != '.' ? consumeDecimalIntegerLiteral(stream) : 0;
		if (stream.peek() == '.') {
			stream.read();
			Number fractionPart = consumeDecimalDigits(stream, true);
			numberValue = numberValue.intValue() + fractionPart.doubleValue();
		}

		if (isExponentIndicator(stream.peek())) {
			Number exponentPart = consumeExponentPart(stream);
			numberValue = numberValue.doubleValue() * Math.pow(10, exponentPart.intValue());
		}

		return numberValue;
	}

	public static Number consumeDecimalDigits(TokenizerStream stream, boolean isAfterDot) throws IOException, ParseException {
		int intValue = 0;
		int i = 0;
		while (isDecimalDigit(stream.peek()) || stream.peek() == '_') {
			if (stream.peek() == '_' && i == 0) {
				throw new ParseException("Expected decimal digit", stream.meta());
			} else if (stream.peek() == '_') {
				stream.read();
				continue;
			}
			intValue = intValue * 10 + stream.read() - '0';
			i++;
		}

		if (isAfterDot && i == 0) {
			throw new ParseException("Expected decimal digit", stream.meta());
		}

		if (isAfterDot) {
			return intValue / Math.pow(10, i);
		} else {
			return intValue;
		}
	}

	public static boolean isDecimalDigit(int ch) {
		return ch >= '0' && ch <= '9';
	}

	private static Number consumeDecimalIntegerLiteral(TokenizerStream stream) throws IOException, ParseException {
		return consumeDecimalDigits(stream, false);
	}

	private static Number consumeExponentPart(TokenizerStream stream) throws IOException, ParseException {
		stream.read();

		int nextChar = stream.peek();
		if (nextChar == '+' || nextChar == '-') {
			stream.read();
		} else if (!isDecimalDigit(nextChar)) {
			throw new ParseException("Expected exponent part", stream.meta());
		}
		int sign = nextChar == '-' ? -1 : 1;

		if (!isDecimalDigit(stream.peek())) {
			throw new ParseException("Expected decimal digit", stream.meta());
		}

		int value = 0;
		while (isDecimalDigit(stream.peek())) {
			value = value * 10 + (stream.read() - '0');
		}

		return value * sign;
	}

	private static boolean isExponentIndicator(int ch) {
		return ch == 'e' || ch == 'E';
	}

}
