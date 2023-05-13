package com.github.webicitybrowser.spec.infra.util;

public final class ASCIIUtil {
	
	private ASCIIUtil() {}
	
	public static boolean isASCIILowerAlpha(int ch) {
		return ch >= 'a' && ch <= 'z';
	}
	
	public static boolean isASCIIUpperAlpha(int ch) {
		return ch >= 'A' && ch <= 'Z';
	}
	
	public static boolean isASCIIAlpha(int ch) {
		return isASCIIUpperAlpha(ch) || isASCIILowerAlpha(ch);
	}
	
	public static boolean isASCIIDigit(int ch) {
		return ch >= '0' && ch <= '9';
	}
	
	public static boolean isASCIIHexDigit(int ch) {
		return
			isASCIIDigit(ch) ||
			(ch >= 'a' && ch <= 'f') ||
			(ch >= 'A' && ch <= 'F');
	}
	
	public static int toASCIILowerCase(int ch) {
		if (isASCIIUpperAlpha(ch)) {
			return ch + 0x20;
		}
		
		return ch;
	}
	
	public static boolean isASCIIWhiteSpace(int ch) {
		switch (ch) {
		case ' ':
		case '\t':
		case '\n':
		case '\f':
		case '\r':
			return true;
		default:
			return false;
		}
	}
	
	public static String stripAndCollapseASCIIWhitespace(String text) {
		return text
			.replaceAll("[\t\n\f\r ]+", " ")
			.replaceAll("^ +", "")
			.replaceAll(" +$", "");
	}

	public static int fromASCIIHexCharacter(int ch) {
		assert isASCIIHexDigit(ch) : "Character must be hex digit!";
		if (ch >= '0' && ch <= '9') {
			return ch - '0';
		} else if (ch >= 'a' && ch <= 'f') {
			return 10 + ch - 'a';
		} else {
			return 10 + ch - 'A';
		}
	}

	public static int fromASCIINumericalCharacter(int ch) {
		return ch - '0';
	}

	public static boolean isVisibleASCIICharacter(int ch) {
		return ch >= 0x21 && ch <= 0x7E;
	}

}
