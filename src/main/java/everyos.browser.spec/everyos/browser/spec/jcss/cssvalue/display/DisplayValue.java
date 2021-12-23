package everyos.browser.spec.jcss.cssvalue.display;

import everyos.browser.spec.jcss.cssom.property.display.DisplayProperty;
import everyos.browser.spec.jcss.cssvalue.CSSValueUtil;
import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.parser.CSSToken;

public final class DisplayValue {
	
	private DisplayValue() {}

	public static ValueParseInfo<DisplayProperty> parse(int off, CSSToken[] cssTokens) {
		//TODO: Support non-legacy display values
		return CSSValueUtil.takeLongestValue(
			LegacyDisplayValue.parse(off, cssTokens));
	}
	
}
