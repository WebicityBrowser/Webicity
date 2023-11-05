package com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp.numeric;

import java.io.IOException;

import com.github.webicitybrowser.ecmaspiral.parser.exception.ParseException;
import com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp.TokenizerStream;

public final class HexTokenizer {
	
	private HexTokenizer() {}

	public static Number consumeHexDigits(TokenizerStream stream) throws IOException, ParseException {
		int value = 0;
		if (!isHexDigit(stream.peek())) {
			throw new ParseException("Expected hex digit", stream.meta());
		}

		boolean separatorAllowed = false;
		while (isHexDigit(stream.peek()) || stream.peek() == '_') {
			separatorAllowed = NumericTokenizer.ignoreSeparator(separatorAllowed, stream);
			value = value * 16 + hexValue(stream.read());
		}
		return value;
	}

	private static int hexValue(int ch) {
		if (ch >= '0' && ch <= '9') {
			return ch - '0';
		} else if (ch >= 'a' && ch <= 'f') {
			return ch - 'a' + 10;
		} else {
			return ch - 'A' + 10;
		}
	}

	private static boolean isHexDigit(int ch) {
		return
			(ch >= '0' && ch <= '9') ||
			(ch >= 'a' && ch <= 'f') ||
			(ch >= 'A' && ch <= 'F');
	}

}
