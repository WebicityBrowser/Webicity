package everyos.browser.spec.jcss.cssvalue.color;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.browser.spec.jcss.parser.HashToken;
import everyos.engine.ribbon.core.graphics.paintfill.Color;

public class HexColorValue {

	public static ValueParseInfo<Color> parse(int off, CSSToken[] cssTokens) {
		if (!(cssTokens[off] instanceof HashToken)) {
			return ValueParseInfo.<Color>empty();
		}
		
		String hex = ((HashToken) cssTokens[off]).getValue();
		Color color = null;
		
		try {
			switch (hex.length()) {
				case 6:
					color = Color.of(
						get6Part(hex, 0),
						get6Part(hex, 2),
						get6Part(hex, 4));
					break;
					
				case 3:
					color = Color.of(
						get3Part(hex, 0),
						get3Part(hex, 1),
						get3Part(hex, 2));
					break;
					
				//TODO: 8 and 4 digits
					
				default:
					System.out.println(hex.length());
					break;
			}
		} catch (NumberFormatException e) {
			color = null; // Probably useless
		}
		
		if (color == null) {
			return ValueParseInfo.<Color>empty();
		} else {
			return new ValueParseInfo<Color>(color, 1);
		}
	}


	private static int get6Part(String hex, int i) {
		return fromHex(hex.charAt(i)) * 16 + fromHex(hex.charAt(i+1));
	}
	
	private static int get3Part(String hex, int i) {
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
