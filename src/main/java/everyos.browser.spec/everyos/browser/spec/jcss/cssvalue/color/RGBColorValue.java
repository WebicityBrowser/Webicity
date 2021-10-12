package everyos.browser.spec.jcss.cssvalue.color;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.imp.Function;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.browser.spec.jcss.parser.CommaToken;
import everyos.browser.spec.jcss.parser.DelimToken;
import everyos.browser.spec.jcss.parser.NumberToken;
import everyos.browser.spec.jcss.parser.PercentageToken;
import everyos.browser.spec.jcss.parser.WhitespaceToken;
import everyos.engine.ribbon.core.graphics.Color;

public final class RGBColorValue {

	private RGBColorValue() {}

	public static ValueParseInfo<Color> parse(int off, CSSToken[] cssTokens) {
		if (!(cssTokens[off] instanceof Function)) {
			return ValueParseInfo.<Color>empty();
		}
		
		Function function = (Function) cssTokens[off];
		if (!(function.getName().equals("rgb") || function.getName().equals("rgba"))) {
			return ValueParseInfo.<Color>empty();
		}
		
		Object[] body = function.getBody();
		
		boolean usePercent = scanForPercent(body);
		boolean useComma = scanForComma(body);
		
		int[] rgba = new int[] {0, 0, 0, 255};
		
		int[] offset = new int[] {0};
		for (int i = 0; i < 3; i++) {
			try {
				rgba[i] = consumeNextValue(body, offset, usePercent, useComma, i == 0, false);
			} catch (IllegalArgumentException e) {
				return ValueParseInfo.<Color>empty();
			}
		}
		
		int before = offset[0];
		try {
			if (!useComma && offset[0] < body.length) {
				/*if (!(body[offset[0]] instanceof WhitespaceToken)) {
					return ValueParseInfo.<Color>empty();
				}*/
				
				while (body[offset[0]] instanceof WhitespaceToken) {
					offset[0]++;
				}
				
				if (!(body[offset[0]] instanceof DelimToken && ((DelimToken) body[offset[0]]).getValue().equals("/"))) {
					return ValueParseInfo.<Color>empty();
				}
				
				offset[0]++;			
			}
			
			rgba[3] = consumeNextValue(body, offset, false, useComma, false, true);
		} catch (IllegalArgumentException e) {
			for (int i = before; i < offset[0]; i++) {
				if (!(body[i] instanceof WhitespaceToken)) {
					return ValueParseInfo.<Color>empty();
				}
			}
		}
		
		for (int i = offset[0]; i < body.length; i++) {
			if (!(body[i] instanceof WhitespaceToken)) {
				return ValueParseInfo.<Color>empty();
			}
		}
		
		return new ValueParseInfo<Color>(new Color(rgba[3], rgba[0], rgba[1], rgba[2]), 1);
	}

	private static boolean scanForPercent(Object[] body) {
		for (int i = 0; i < body.length; i++) {
			if (body[i] instanceof NumberToken) {
				return false;
			} else if (body[i] instanceof PercentageToken) {
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean scanForComma(Object[] body) {
		for (int i = 0; i < body.length; i++) {
			if (body[i] instanceof CommaToken) {
				return true;
			}
		}
		
		return false;
	}

	private static int consumeNextValue(Object[] body, int[] off, boolean usePercent, boolean useComma, boolean first, boolean alpha) throws IllegalArgumentException {
		int i = off[0];
		
		if (i >= body.length) {
			throw new IllegalArgumentException();
		}
		
		/*if (!(first || useComma || body[i] instanceof WhitespaceToken)) {
			throw new IllegalArgumentException();
		}*/
		
		while (i < body.length && body[i] instanceof WhitespaceToken) {
			i++;
		}
		
		if (useComma) {
			if (i >= body.length) {
				throw new IllegalArgumentException();
			}
			
			if (!(first || body[i] instanceof CommaToken)) {
				throw new IllegalArgumentException();
			}
			
			if (!first) {
				i++;
			}
			
			while (i < body.length && body[i] instanceof WhitespaceToken) {
				i++;
			}
		}
		
		if (i >= body.length) {
			throw new IllegalArgumentException();
		}
		
		int fin;
		if (usePercent || (alpha && body[i] instanceof PercentageToken)) {
			if (!(body[i] instanceof PercentageToken)) {
				throw new IllegalArgumentException();
			}
			
			fin = (int) (((PercentageToken) body[i]).getAsFloat() * 255);
		} else {
			if (!(body[i] instanceof NumberToken)) {
				throw new IllegalArgumentException();
			}
			
			if (alpha) {
				fin = (int) (((NumberToken) body[i]).getAsFloat() * 255);
			} else {
				fin = ((NumberToken) body[i]).getAsInt();
			}
		}
		
		i++;
		
		off[0] = i;
		
		return fin;
	}
}
