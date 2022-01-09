package everyos.browser.spec.jcss.cssvalue.font;

import everyos.browser.spec.jcss.cssvalue.CSSValueUtil;
import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.cssvalue.common.LengthPercentageValue;
import everyos.browser.spec.jcss.cssvalue.common.Sizing;
import everyos.browser.spec.jcss.parser.CSSToken;

public class FontSizeValue {

	public static ValueParseInfo<Sizing> parse(int off, CSSToken[] cssTokens) {
		//TODO: Support other keywords
		return CSSValueUtil.takeLongestValue(
			LengthPercentageValue.parse(off, cssTokens));
	}

}
