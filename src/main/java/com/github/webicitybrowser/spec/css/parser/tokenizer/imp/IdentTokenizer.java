package com.github.webicitybrowser.spec.css.parser.tokenizer.imp;

import java.io.IOException;

import com.github.webicitybrowser.spec.css.parser.tokens.AtKeywordToken;
import com.github.webicitybrowser.spec.css.parser.tokens.FunctionToken;
import com.github.webicitybrowser.spec.css.parser.tokens.HashToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.parser.tokens.HashToken.HashTypeFlag;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;

public final class IdentTokenizer {
	
	private static final int MAX_CODE_POINT = 0x10FFFF;

	private IdentTokenizer() {}
	
	public static Token consumeCommercialAtSign(ReaderHandle reader) throws IOException {
		if (wouldStartAnIdentSequence(reader)) {
			String value = consumeAnIdentSequence(reader);
			return createAtKeywordToken(value);
		}
		
		return SharedTokenizer.createDelimToken('@');
	}
	
	public static Token consumeReverseSolidusSign(ReaderHandle reader) throws IOException {
		if (SharedTokenizer.isValidEscapeSequence('\\', reader)) {
			reader.unread('\\');
			return consumeAnIdentLikeToken(reader);
		}
		
		//TODO: Parse error
		return SharedTokenizer.createDelimToken('\\');
	}
	
	public static Token consumeHashSign(ReaderHandle reader) throws IOException {
		if (isIdentCodePoint(reader.peek()) || SharedTokenizer.wouldStartValidEscapeSequence(reader)) {
			return consumeHashToken(reader);
		}
		
		return SharedTokenizer.createDelimToken('#');
	}
	
	public static Token consumeAnIdentLikeToken(ReaderHandle reader) throws IOException {
		String identName = consumeAnIdentSequence(reader);
		
		if (identName.equalsIgnoreCase("url") && reader.peek() == '(') {
			return consumeAUrlFunctionIdent(reader, identName);
		}
		
		if (reader.peek() == '(') {
			reader.read();
			return createFunctionToken(identName);
		}
		
		return createIdentToken(identName);
	}

	public static String consumeAnIdentSequence(ReaderHandle reader) throws IOException {
		StringBuilder result = new StringBuilder();
		while (true) {
			int ch = reader.peek();
			if (isIdentCodePoint(ch)) {
				reader.read();
				result.appendCodePoint(ch);
			} else if (SharedTokenizer.isValidEscapeSequence(ch, reader)) {
				reader.read();
				result.appendCodePoint(SharedTokenizer.consumeAnEscapedCodePoint(reader));
			} else {
				return result.toString();
			}
		}
	}
	
	public static boolean isValidIdentSequence(int ch, ReaderHandle reader) throws IOException {
		switch(ch) {
		case '-':
			reader.read();
			if (
				isIdentStartCodePoint(reader.peek()) ||
				reader.peek() == '-' ||
				SharedTokenizer.wouldStartValidEscapeSequence(reader)
			) {
				reader.unread(ch);
				return true;
			}
			reader.unread(ch);
			return false;
		case '/':
			return SharedTokenizer.isValidEscapeSequence(ch, reader);
		default:
			return isIdentStartCodePoint(ch);
		}
	}
	
	public static boolean wouldStartAnIdentSequence(ReaderHandle reader) throws IOException {
		int ch = reader.read();
		boolean wouldStart = isValidIdentSequence(ch, reader);
		reader.unread(ch);
		
		return wouldStart;
	}
	
	public static boolean isIdentStartCodePoint(int ch) {
		return
			ASCIIUtil.isASCIIAlpha(ch) ||
			(ch >= 0x80 && ch <= MAX_CODE_POINT) ||
			ch == '_';
	}
	
	private static boolean isIdentCodePoint(int ch) {
		return
			isIdentStartCodePoint(ch) ||
			ASCIIUtil.isASCIIDigit(ch) ||
			ch == '-';
	}
	
	private static Token consumeAUrlFunctionIdent(ReaderHandle reader, String functionName) throws IOException {
		reader.read();
		consumeLeadingWhitespace(reader);
		
		int ch1 = reader.read();
		int ch2 = reader.peek();
		reader.unread(ch1);
		if (
			ch1 == '"' ||
			ch1 == '\'' ||
			(SharedTokenizer.isWhitespace(ch1) && ch2 == '"') ||
			(SharedTokenizer.isWhitespace(ch1) && ch2 == '\'')
		) {
			return createFunctionToken(functionName);
		} else {
			return URLTokenizer.consumeAURLToken(reader);
		}
	}
	
	private static void consumeLeadingWhitespace(ReaderHandle reader) throws IOException {
		while (true) {
			int ch1 = reader.read();
			int ch2 = reader.peek();
			reader.unread(ch1);
			if (SharedTokenizer.isWhitespace(ch1) && SharedTokenizer.isWhitespace(ch2)) {
				reader.read();
			} else {
				break;
			}
		}
	}

	private static Token consumeHashToken(ReaderHandle reader) throws IOException {
		HashTypeFlag flag = wouldStartAnIdentSequence(reader) ?
			HashTypeFlag.ID :
			HashTypeFlag.UNRESTRICTED;
		String value = consumeAnIdentSequence(reader);
		
		return createHashToken(value, flag);
	}
	
	private static IdentToken createIdentToken(String value) {
		return () -> value;
	}
	
	private static FunctionToken createFunctionToken(String value) {
		return () -> value;
	}
	
	private static AtKeywordToken createAtKeywordToken(String value) {
		return () -> value;
	}
	
	private static HashToken createHashToken(String value, HashTypeFlag flag) {
		return new HashToken() {
			
			@Override
			public String getValue() {
				return value;
			}
			
			@Override
			public HashTypeFlag getTypeFlag() {
				return flag;
			}
			
		};
	}
	
}
