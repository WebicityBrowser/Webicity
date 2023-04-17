package com.github.webicitybrowser.spiderhtml.util;

public final class ASCIIUtil {
	
	private ASCIIUtil() {}
	
	public static boolean isASCIIAlpha(int ch) {
		return (ch >= 'A' && ch <= 'z');
	}
	
	public static boolean isASCIIUpperAlpha(int ch) {
		return (ch >= 'A' && ch <= 'Z');
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

}
