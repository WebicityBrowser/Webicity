package everyos.browser.spec.jcss.cssom.property.fontsize;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.cssvalue.common.Sizing;
import everyos.browser.spec.jcss.cssvalue.font.FontSizeValue;
import everyos.browser.spec.jcss.parser.CSSToken;

public final class FontSizePropertyParser {
	
	private FontSizePropertyParser() {}
	
	public static FontSizeProperty parse(CSSToken[] cssTokens) {
		ValueParseInfo<Sizing> fontSizeParseInfo = FontSizeValue.parse(0, cssTokens);
		
		if (fontSizeParseInfo.failed() || fontSizeParseInfo.getNumberConsumedTokens() != cssTokens.length) {
			return null;
		}
		
		return new FontSizeProperty(fontSizeParseInfo.getValue().get());
	}
	
}
