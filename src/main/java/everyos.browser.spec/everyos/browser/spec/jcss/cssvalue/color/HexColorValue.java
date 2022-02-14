package everyos.browser.spec.jcss.cssvalue.color;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.browser.spec.jcss.parser.HashToken;

public class HexColorValue {

	public static ValueParseInfo<CSSColor> parse(int off, CSSToken[] cssTokens) {
		// Make sure that we were actually passed a hex value
		if (!(cssTokens[off] instanceof HashToken)) {
			return ValueParseInfo.<CSSColor>empty();
		}
		
		String hex = ((HashToken) cssTokens[off]).getValue();
		try {
			return new ValueParseInfo<CSSColor>(parseHex(hex), 1);
		} catch (NumberFormatException e) {
			return ValueParseInfo.<CSSColor>empty();
		}
	}


	private static CSSColor parseHex(String hex) {
		switch (hex.length()) {
			case 8:
				return parse8DigitHex(hex);
		
			case 6:
				return parse6DigitHex(hex);
				
			case 4:
				return parse4DigitHex(hex);
				
			case 3:
				return parse3DigitHex(hex);
				
			//TODO: 8 and 4 digits
				
			default:
				throw new NumberFormatException("Could not recognize hex format!");
		}
	}


	private static CSSColor parse8DigitHex(String hex) {
		return CSSColor.ofRGBA8(
			getDoubleHexDigit(hex, 0),
			getDoubleHexDigit(hex, 2),
			getDoubleHexDigit(hex, 4),
			getDoubleHexDigit(hex, 6));
	}
	
	private static CSSColor parse6DigitHex(String hex) {
		return CSSColor.ofRGB8(
			getDoubleHexDigit(hex, 0),
			getDoubleHexDigit(hex, 2),
			getDoubleHexDigit(hex, 4));
	}
	
	private static CSSColor parse4DigitHex(String hex) {
		return CSSColor.ofRGBA8(
			getHexDigit(hex, 0),
			getHexDigit(hex, 1),
			getHexDigit(hex, 2),
			getHexDigit(hex, 3));
	}
	
	private static CSSColor parse3DigitHex(String hex) {
		return CSSColor.ofRGB8(
			getHexDigit(hex, 0),
			getHexDigit(hex, 1),
			getHexDigit(hex, 2));
	}


	private static int getDoubleHexDigit(String hex, int i) {
		return fromHex(hex.charAt(i)) * 16 + fromHex(hex.charAt(i+1));
	}
	
	private static int getHexDigit(String hex, int i) {
		return fromHex(hex.charAt(i)) * 16 + fromHex(hex.charAt(i));
	}


	private static int fromHex(char ch) {
		if (ch >= '0' && ch <= '9') {
			return ch - '0';
		} else if (ch >= 'a' && ch <= 'f') {
			return ch - 'a' + 10;
		} else if (ch >= 'A' && ch <= 'F') {
			return ch - 'A' + 10;
		}
		
		throw new NumberFormatException("Could not convert character '"+ch+"' from hex!");
	}
}
