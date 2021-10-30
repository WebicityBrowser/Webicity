package everyos.browser.spec.jcss.cssom.property.display;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.cssvalue.display.DisplayValue;
import everyos.browser.spec.jcss.parser.CSSToken;

public final class DisplayPropertyParser {
	
	private DisplayPropertyParser() {}
	
	public static DisplayProperty parse(CSSToken[] cssTokens) {
		ValueParseInfo<DisplayProperty> displayParseInfo = DisplayValue.parse(0, cssTokens);
		
		if (displayParseInfo.failed() || displayParseInfo.getNumberConsumedTokens() != cssTokens.length) {
			return null;
		}
		
		return displayParseInfo.getValue().get();
	}
	
}
