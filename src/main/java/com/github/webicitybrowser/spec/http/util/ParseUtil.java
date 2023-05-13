package com.github.webicitybrowser.spec.http.util;

import java.io.IOException;
import java.io.PushbackInputStream;

import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;

public class ParseUtil {
	
	public static boolean isObsText(int ch) {
		return ch >= 0x80 && ch <= 0xFF;
	}

	public static int eatDigit(PushbackInputStream inputStream) throws IOException {
		int ch = inputStream.read();
		if (!ASCIIUtil.isASCIIDigit(ch)) {
			expect("a digit", ch);
		}
		
		return ASCIIUtil.fromASCIINumericalCharacter(ch);
	}
	
	public static void eatString(PushbackInputStream inputStream, String string) throws IOException {
		for (char expected: string.toCharArray()) {
			int ch = inputStream.read();
			expect("char '" + ch + "'", expected, ch);
		}
	}
	
	public static void eatSpace(PushbackInputStream inputStream) throws IOException {
		int ch = inputStream.read();
		expect("space", ' ', ch);
	}
	
	public static void eatCRLF(PushbackInputStream inputStream) throws IOException {
		int ch = inputStream.read();
		expect("CRLF (carriage return)", '\r', ch);
		ch = inputStream.read();
		expect("CRLF (line feed)", '\n', ch);
	}
	
	public static String eatToken(PushbackInputStream inputStream) throws IOException {
		int ch;
		StringBuilder tokenBuilder = new StringBuilder();
		while (isTokenChar(ch = inputStream.read())) {
			tokenBuilder.appendCodePoint(ch);
		}
		if (tokenBuilder.isEmpty()) {
			expect("a token character", ch);
		}
		inputStream.unread(ch);
		
		return tokenBuilder.toString();
	}
	
	public static void eatOptionalWhitespace(PushbackInputStream inputStream) throws IOException {
		int ch;
		while ((ch = inputStream.read()) == ' ' || ch == 0x09);
		inputStream.unread(ch);
	}

	public static void expect(String expectedName, int ch) {
		if (ch == -1) {
			throw new RuntimeException("HTTP Response: Expected " + expectedName + ", got EOF!");
		}
		throw new RuntimeException("HTTP Response: Expected " + expectedName + ", got codepoint " + ch + "!");
	}
	
	public static void expect(String expectedName, int expected, int ch) {
		if (ch == expected) {
			return;
		}
		expect(expectedName, ch);
	}
	
	private static boolean isTokenChar(int ch) {
		if (ASCIIUtil.isASCIIAlpha(ch) || ASCIIUtil.isASCIIDigit(ch)) {
			return true;
		}
		
		char[] symbols = new char[] {
			'!', '#', '$', '%', '&', '\'', '*',
			'+', '-', '.', '^', '_', '~'
		};
		
		for (char compared: symbols) {
			if (ch == compared) {
				return true;
			}
		}
		
		return false;
	}
	
}
