package com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp.numeric;

import java.io.IOException;

import com.github.webicitybrowser.ecmaspiral.parser.exception.ParseException;
import com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp.TokenizerStream;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.NumericToken;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.Token;

public final class NumericTokenizer {
	
	private NumericTokenizer() {}

	public static Token consumeNumericToken(TokenizerStream stream) throws IOException, ParseException {
		Number numberValue;
		if (stream.peek() == '0') {
			stream.read();
			numberValue = consumeZeroSpecial(stream);
		} else {
			numberValue = DecimalTokenizer.consumeDecimalLiteral(stream);
		}
		return new NumericToken(numberValue, stream.meta());
	}

	public static boolean ignoreSeparator(boolean separatorAllowed, TokenizerStream stream) throws IOException, ParseException {
		if (stream.peek() == '_') {
			if (!separatorAllowed) {
				throw new ParseException("Expected digit, got separator!", stream.meta());
			}

			stream.read();
			return false;
		}

		return true;
	}

	private static Number consumeZeroSpecial(TokenizerStream stream) throws IOException, ParseException {
		if (stream.peek() == 'o' || stream.peek() == 'O') {
			stream.read();
			return OctalTokenizer.consumeOctalDigits(stream);
		} else if (stream.peek() == 'x' || stream.peek() == 'X') {
			stream.read();
			return HexTokenizer.consumeHexDigits(stream);
		} else if (stream.peek() == 'b' || stream.peek() == 'B') {
			stream.read();
			return BinaryTokenizer.consumeBinaryDigits(stream);
		} else if (OctalTokenizer.isOctalDigit(stream.peek())) {
			return OctalTokenizer.consumeOctalDigits(stream);
		} else if (DecimalTokenizer.isDecimalDigit(stream.peek())) {
			return DecimalTokenizer.consumeDecimalDigits(stream, false);
		} else if (stream.peek() == '.') {
			stream.read();
			return DecimalTokenizer.consumeDecimalDigits(stream, true);
		} else {
			return 0;
		}
	}

	public static boolean isNumericStart(int ch) {
		return DecimalTokenizer.isDecimalDigit(ch) || ch == '.';
	}

}
