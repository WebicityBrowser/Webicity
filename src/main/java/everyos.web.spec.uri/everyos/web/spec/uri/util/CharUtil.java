package everyos.web.spec.uri.util;

public final class CharUtil {

	private CharUtil() {}
	
	public static boolean isASCIIAlpha(int ch) {
		return (ch >= 'A' && ch <= 'z');
	}
	
	public static boolean isASCIINumeric(int ch) {
		return (ch >= '0' && ch <= '9');
	}
	
	public static boolean isASCIIAlphanumeric(int ch) {
		return isASCIIAlpha(ch) || isASCIINumeric(ch);
	}

	public static int toASCIILowerCase(int ch) {
		if (isASCIIAlpha(ch)) {
			return Character.toLowerCase(ch);
		}
		
		return ch;
	}

	public static boolean isASCIIHexDigit(int ch) {
		return
			isASCIIAlpha(ch) ||
			(ch >= 'A' && ch <= 'F') ||
			(ch >= 'a' && ch <= 'f');
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
