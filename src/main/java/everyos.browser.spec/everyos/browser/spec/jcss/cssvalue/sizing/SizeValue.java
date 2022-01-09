package everyos.browser.spec.jcss.cssvalue.sizing;

import everyos.browser.spec.jcss.cssvalue.CSSValueUtil;
import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.cssvalue.common.LengthPercentageValue;
import everyos.browser.spec.jcss.cssvalue.common.Sizing;
import everyos.browser.spec.jcss.parser.CSSToken;

public final class SizeValue {
	
	private SizeValue() {}

	public static ValueParseInfo<Sizing> parse(int off, CSSToken[] cssTokens) {
		//TODO: Support other size values
		return CSSValueUtil.takeLongestValue(
			LengthPercentageValue.parse(off, cssTokens));
	}

}
