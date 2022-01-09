package everyos.browser.spec.jcss.cssvalue.common;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.browser.spec.jcss.parser.DimensionToken;
import everyos.browser.spec.jcss.parser.NumberToken;

public final class LengthValue {

	private LengthValue() {}
	
	public static ValueParseInfo<Sizing> parse(int off, CSSToken[] cssTokens) {
		//TODO: 0 should take low priority against numbers
		if (isZeroToken(off, cssTokens)) {
			return new ValueParseInfo<Sizing>(e -> 0, 1);
		}
		
		if (!isDimensionToken(off, cssTokens)) {
			return ValueParseInfo.empty();
		}
		
		DimensionToken dimensionToken = (DimensionToken) cssTokens[off];
		
		Sizing sizing = parseDimensionToken(dimensionToken);
		if (sizing != null) {
			return new ValueParseInfo<Sizing>(sizing, 1);
		}
		
		//TODO: Pay attention to the actual value. This is a stub.
		return ValueParseInfo.empty();
	}

	private static boolean isZeroToken(int off, CSSToken[] cssTokens) {
		return
			cssTokens.length == 1 &&
			cssTokens[off] instanceof NumberToken &&
			((NumberToken) cssTokens[off]).getAsInt() == 0;
	}
	
	private static boolean isDimensionToken(int off, CSSToken[] cssTokens) {
		return
			cssTokens.length == 1 &&
			cssTokens[off] instanceof DimensionToken;
	}
	
	private static Sizing parseDimensionToken(DimensionToken dimensionToken) {
		//TODO: Handle integers in addition to floats
		switch(dimensionToken.getUnit()) {
			// Relative Sizing
			case "em":
				return e -> (int) (dimensionToken.getValue().floatValue() * 16);
				
			// Absolute Sizing
			case "cm":
				return e -> (int) (dimensionToken.getValue().floatValue() * (96/2.54));
			case "mm":
				return e -> (int) (dimensionToken.getValue().floatValue() * (9.6/2.54));
			case "Q":
				return e -> (int) (dimensionToken.getValue().floatValue() * (96/2.54/40));	
			case "in":
				return e -> (int) (dimensionToken.getValue().floatValue() * 96);
			case "pc":
				return e -> (int) (dimensionToken.getValue().floatValue() * (96/6.0));
			case "pt":
				return e -> (int) (dimensionToken.getValue().floatValue() * (96/72.0));
			case "px":
				return e -> dimensionToken.getValue().intValue();
			default:
				return null;
		}
	}

}
