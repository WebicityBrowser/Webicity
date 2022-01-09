package everyos.browser.spec.jcss.cssvalue.common;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.browser.spec.jcss.parser.PercentageToken;

public final class PercentageValue {

	private PercentageValue() {}
	
	public static ValueParseInfo<Sizing> parse(int off, CSSToken[] cssTokens) {
		//TODO: 0 should take low priority against numbers
		if (!isPercentageToken(off, cssTokens)) {
			return ValueParseInfo.empty();
		}
		
		return new ValueParseInfo<Sizing>(
			e -> (int) (((PercentageToken) cssTokens[off]).getAsFloat() * e),
			1);
	}

	private static boolean isPercentageToken(int off, CSSToken[] cssTokens) {
		return
			cssTokens.length == 1 &&
			cssTokens[off] instanceof PercentageToken;
	}
}