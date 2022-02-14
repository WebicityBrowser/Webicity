package everyos.browser.spec.jcss.cssvalue.color;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.imp.Function;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.browser.spec.jcss.parser.CommaToken;
import everyos.browser.spec.jcss.parser.DelimToken;
import everyos.browser.spec.jcss.parser.NumberToken;
import everyos.browser.spec.jcss.parser.PercentageToken;
import everyos.browser.spec.jcss.parser.WhitespaceToken;

public final class RGBColorValue {

	private RGBColorValue() {}
	
	//TODO: There are a few cases where an OOB can occur - patch those up

	public static ValueParseInfo<CSSColor> parse(int off, CSSToken[] cssTokens) {
		if (!isValidRGBColorValue(off, cssTokens)) {
			return ValueParseInfo.<CSSColor>empty();
		}
		
		Object[] body = ((Function) cssTokens[off]).getBody();
		
		boolean usePercent = scanForPercent(body);
		boolean useComma = scanForComma(body);
		
		int[] rgba = new int[] { 0, 0, 0, 255 };
		int[] offset = new int[] { 0 };
		
		if (!parseRGBPortion(body, offset, rgba, usePercent, useComma)) {
			return ValueParseInfo.<CSSColor>empty();
		}
		
		int alphaPortion = tryToConsumeAlphaPortion(body, offset, useComma);
		if (alphaPortion != -1) {
			rgba[3] = alphaPortion;
		}
		
		// Ensure only whitespace remains in the function body
		eatWhitespace(body, offset);
		if (offset[0] != body.length) {
			return ValueParseInfo.<CSSColor>empty();
		}
		
		return new ValueParseInfo<CSSColor>(CSSColor.ofRGBA8(rgba[0], rgba[1], rgba[2], rgba[3]), 1);
	}

	private static boolean parseRGBPortion(Object[] body, int[] offset, int[] rgba, boolean usePercent, boolean useComma) {
		for (int i = 0; i < 3; i++) {
			try {
				rgba[i] = consumeNextValue(body, offset, usePercent, useComma && !(i == 0), false);
			} catch (IllegalArgumentException e) {
				return false;
			}
		}
		
		return true;
	}
	
	private static int tryToConsumeAlphaPortion(Object[] body, int[] offset, boolean useComma) {
		int[] offsetBuffer = new int[offset[0]];
		
		try {
			int result = consumeAlphaPortion(body, offset, useComma);
			offset[0] = offsetBuffer[0];
			
			return result;
		} catch (IllegalArgumentException e) {
			return -1;
		}
	}
	
	private static int consumeAlphaPortion(Object[] body, int[] offset, boolean useComma) {
		if (!useComma && offset[0] < body.length) {
			eatWhitespace(body, offset);
			consumeSlashToken(body, offset);
		}
		
		int result = consumeNextValue(body, offset, false, useComma, true);
		
		return result;
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
	
	private static boolean isValidRGBColorValue(int off, CSSToken[] cssTokens) {
		if (!(cssTokens[off] instanceof Function)) {
			return false;
		}
		
		Function function = (Function) cssTokens[off];
		return
			function.getName().equals("rgb") ||
			function.getName().equals("rgba");
	}

	//TODO: Shorten # of parameters
	private static int consumeNextValue(Object[] body, int[] offset, boolean usePercent, boolean useComma, boolean alpha)
		throws IllegalArgumentException {
		
		eatWhitespace(body, offset);
		
		if (useComma) {
			ensureMoreValuesRemain(body, offset[0]);
			consumeCommaToken(body, offset);
			eatWhitespace(body, offset);
		}
		
		ensureMoreValuesRemain(body, offset[0]);
		
		if (usePercent || (alpha && body[offset[0]] instanceof PercentageToken)) {
			PercentageToken percentageToken = consumePercentageToken(body, offset);
			
			return (int) (percentageToken.getAsFloat() * 255);
		} else {
			NumberToken numberToken = consumeNumberToken(body, offset);
			
			if (alpha) {
				return (int) (numberToken.getAsFloat() * 255);
			} else {
				return numberToken.getAsInt();
			}
		}
	}

	private static void ensureMoreValuesRemain(Object[] body, int offset) {
		if (offset >= body.length) {
			throw new IllegalArgumentException();
		}
	}
	
	private static void eatWhitespace(Object[] body, int[] offset) {
		while (offset[0] < body.length && body[offset[0]] instanceof WhitespaceToken) {
			offset[0]++;
		}
	}
	
	private static void consumeCommaToken(Object[] body, int[] offset) {
		ensureTokenIsCommaToken(body, offset[0]++);
	}
	
	private static void ensureTokenIsCommaToken(Object[] body, int offset) {
		if (!(body[offset] instanceof CommaToken)) {
			throw new IllegalArgumentException();
		}
	}
	
	private static PercentageToken consumePercentageToken(Object[] body, int[] offset) {
		ensureTokenIsPercentageToken(body, offset[0]);
		
		return (PercentageToken) body[offset[0]++];
	}
	
	private static void ensureTokenIsPercentageToken(Object[] body, int offset) {
		if (!(body[offset] instanceof PercentageToken)) {
			throw new IllegalArgumentException();
		}
	}
	
	private static NumberToken consumeNumberToken(Object[] body, int[] offset) {
		ensureTokenIsNumberToken(body, offset[0]);
		
		return (NumberToken) body[offset[0]++];
	}
	
	private static void ensureTokenIsNumberToken(Object[] body, int offset) {
		if (!(body[offset] instanceof NumberToken)) {
			throw new IllegalArgumentException();
		}
	}
	
	private static void consumeSlashToken(Object[] body, int[] offset) {
		ensureTokenIsSlashToken(body, offset[0]++);
	}
	
	private static void ensureTokenIsSlashToken(Object[] body, int offset) {
		boolean tokenIsSlash =
			body[offset] instanceof DelimToken &&
			((DelimToken) body[offset]).getValue().equals("/");
		
		if (!tokenIsSlash) {
			throw new IllegalArgumentException();
		}
	}
	
}
