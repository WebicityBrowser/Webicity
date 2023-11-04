package com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp.numeric;

import java.io.IOException;

import com.github.webicitybrowser.ecmaspiral.parser.exception.ParseException;
import com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp.TokenizerStream;

public final class OctalTokenizer {
	
	private OctalTokenizer() {}

	public static Number consumeOctalDigits(TokenizerStream stream) throws IOException, ParseException {
		int value = 0;
		if (!isOctalDigit(stream.peek())) {
			throw new ParseException("Expected octal digit", stream.meta());
		}
		while (isOctalDigit(stream.peek())) {
			value = value * 8 + (stream.read() - '0');
		}
		return value;
	}

	public static boolean isOctalDigit(int ch) {
		return ch >= '0' && ch <= '7';
	}

}
