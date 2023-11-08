package com.github.webicitybrowser.spec.css.parser.tokenizer.imp;

import java.io.IOException;
import java.math.BigInteger;

import com.github.webicitybrowser.spec.css.parser.tokens.CDCToken;
import com.github.webicitybrowser.spec.css.parser.tokens.DimensionToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberTypeFlag;
import com.github.webicitybrowser.spec.css.parser.tokens.PercentageToken;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;

public final class NumberTokenizer {

	private NumberTokenizer() {}
	
	public static Token consumePlusSign(ReaderHandle reader) throws IOException {
		if (ASCIIUtil.isASCIIDigit(reader.peek())) {
			reader.unread('+');
			return consumeANumericToken(reader);
		} else {
			return SharedTokenizer.createDelimToken('+');
		}
	}
	
	public static Token consumeMinusSign(ReaderHandle reader) throws IOException {
		if (ASCIIUtil.isASCIIDigit(reader.peek())) {
			reader.unread('-');
			return consumeANumericToken(reader);
			//TODO
		}
		
		{
			int ch1 = reader.read();
			int ch2 = reader.read();
			if (ch1 == '-' && ch2 == '>') {
				return new CDCToken() {};
			}
			reader.unread(ch2);
			reader.unread(ch1);
		}
		
		if (IdentTokenizer.isValidIdentSequence('-', reader)) {
			reader.unread('-');
			return IdentTokenizer.consumeAnIdentLikeToken(reader);
		}
		
		return SharedTokenizer.createDelimToken('-');
	}
	
	public static Token consumeFullStopSign(ReaderHandle reader) throws IOException {
		if (wouldStartANumber('.', reader)) {
			reader.unread('.');
			return consumeANumericToken(reader);
		}
		return SharedTokenizer.createDelimToken('.');
	}
	
	public static Token consumeANumericToken(ReaderHandle reader) throws IOException {
		Number number = consumeANumber(reader);
		
		if (IdentTokenizer.wouldStartAnIdentSequence(reader)) {
			String unit = IdentTokenizer.consumeAnIdentSequence(reader);
			return createDimensionToken(number, unit);
		}
		
		if (reader.peek() == '%') {
			reader.read();
			return createPercentageToken(number);
		}
		
		return createNumberToken(number);
	}
	
	private static Number consumeANumber(ReaderHandle reader) throws IOException {
		NumberTypeFlag type = NumberTypeFlag.INTEGER;
		StringBuilder repr = new StringBuilder();
		if (reader.peek() == '+' || reader.peek() == '-') {
			repr.appendCodePoint(reader.read());
		}
		
		while (ASCIIUtil.isASCIIDigit(reader.peek())) {
			repr.appendCodePoint(reader.read());
		}
		
		type = consumeDecimalPointIfPresent(reader, repr, type);
		type = consumeExponentIfPresent(reader, repr, type);
		
		if (type == NumberTypeFlag.INTEGER) {
			if (repr.length() > 9) {
				BigInteger bigInt = new BigInteger(repr.toString());
				if (bigInt.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
					return Integer.MAX_VALUE;
				}
			}
			return Integer.valueOf(repr.toString());
		} else {
			return Double.parseDouble(repr.toString());
		}
	}

	private static NumberTypeFlag consumeDecimalPointIfPresent(
		ReaderHandle reader, StringBuilder repr, NumberTypeFlag type
	) throws IOException {
		int ch1 = reader.read();
		int ch2 = reader.read();
		if (ch1 == '.' && ASCIIUtil.isASCIIDigit(ch2)) {
			repr.appendCodePoint(ch1);
			repr.appendCodePoint(ch2);
			while (ASCIIUtil.isASCIIDigit(reader.peek())) {
				repr.appendCodePoint(reader.read());
			}
			return NumberTypeFlag.NUMBER;
		} else {
			reader.unread(ch2);
			reader.unread(ch1);
			return type;
		}
	}
	
	private static NumberTypeFlag consumeExponentIfPresent(
		ReaderHandle reader, StringBuilder repr, NumberTypeFlag type
	) throws IOException {
		int ch1 = reader.read();
		int ch2 = reader.read();
		int ch3 = reader.read();
		if (isExponentStart(ch1, ch2, ch3)) {
			consumeExponent(reader, repr, ch1, ch2, ch3);
			
			return NumberTypeFlag.NUMBER;
		} else {
			reader.unread(ch3);
			reader.unread(ch2);
			reader.unread(ch1);
			return type;
		}
	}

	private static void consumeExponent(
		ReaderHandle reader, StringBuilder repr, int ch1, int ch2, int ch3
	) throws IOException {
		if (!(ch2 == '+' || ch2 == '-')) {
			reader.unread(ch3);
			ch3 = ch2;
			ch2 = '+';
		}
		repr.appendCodePoint(ch1);
		repr.appendCodePoint(ch2);
		repr.appendCodePoint(ch3);
		while (ASCIIUtil.isASCIIDigit(reader.peek())) {
			repr.appendCodePoint(reader.read());
		}
	}

	private static boolean isExponentStart(int ch1, int ch2, int ch3) {
		return 
			(ch1 == 'e' || ch1 == 'E') &&
			(
				((ch2 == '+' || ch2 == '-') && ASCIIUtil.isASCIIDigit(ch3)) ||
				ASCIIUtil.isASCIIDigit(ch2)
			);
	}

	private static boolean wouldStartANumber(int ch1, ReaderHandle reader) throws IOException {
		int ch2 = reader.read();
		int ch3 = reader.peek();
		reader.unread(ch2);
		
		switch(ch1) {
		case '+':
		case '-':
			if (ASCIIUtil.isASCIIDigit(ch2)) {
				return true;
			}
			return
				ch2 == '.' &&
				ASCIIUtil.isASCIIDigit(ch3);
		case '.':
			return ASCIIUtil.isASCIIDigit(ch2);
		default:
			return ASCIIUtil.isASCIIDigit(ch1);
		}
	}
	
	private static DimensionToken createDimensionToken(Number number, String unit) {
		NumberTypeFlag flag = number instanceof Integer ?
			NumberTypeFlag.INTEGER :
			NumberTypeFlag.NUMBER;
		
		return new DimensionToken() {
			@Override
			public Number getValue() {
				return number;
			}

			@Override
			public NumberTypeFlag getTypeFlag() {
				return flag;
			}

			@Override
			public String getUnit() {
				return unit;
			}	
		};
	}
	
	private static NumberToken createNumberToken(Number number) {
		NumberTypeFlag flag = number instanceof Integer ?
			NumberTypeFlag.INTEGER :
			NumberTypeFlag.NUMBER;
		
		return new NumberToken() {
			@Override
			public Number getValue() {
				return number;
			}

			@Override
			public NumberTypeFlag getTypeFlag() {
				return flag;
			}	
		};
	}
	
	private static PercentageToken createPercentageToken(Number number) {
		return () -> number;
	}
	
}
