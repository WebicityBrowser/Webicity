package everyos.browser.spec.jcss.cssvalue.sizing;

import everyos.browser.spec.jcss.cssvalue.CSSValueUtil;
import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.parser.CSSToken;

public final class LengthPercentageValue {
	
	private LengthPercentageValue() {}

	public static ValueParseInfo<Sizing> parse(int off, CSSToken[] cssTokens) {
		//TODO: Support percentage values
		return CSSValueUtil.takeLongestValue(
			LengthValue.parse(off, cssTokens));
	}
	
}
