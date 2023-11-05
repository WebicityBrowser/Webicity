package com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp.numeric;

import java.io.IOException;

import com.github.webicitybrowser.ecmaspiral.parser.exception.ParseException;
import com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp.TokenizerStream;

public final class BinaryTokenizer {
	
	private BinaryTokenizer() {}

	public static Number consumeBinaryDigits(TokenizerStream stream) throws IOException, ParseException {
		if (!isBinaryDigit(stream.peek())) {
			throw new ParseException("Expected binary digit", stream.meta());
		}

		int value = 0;
		boolean separatorAllowed = false;
		while (isBinaryDigit(stream.peek()) || stream.peek() == '_') {
			separatorAllowed = NumericTokenizer.ignoreSeparator(separatorAllowed, stream);
			if (!separatorAllowed) continue;

			value = value * 2 + (stream.read() - '0');
		}

		return value;
	}

	private static boolean isBinaryDigit(int ch) {
		return ch == '0' || ch == '1';
	}

}
