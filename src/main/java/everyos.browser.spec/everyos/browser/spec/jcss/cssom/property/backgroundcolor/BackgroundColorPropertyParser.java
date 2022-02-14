package everyos.browser.spec.jcss.cssom.property.backgroundcolor;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.cssvalue.color.CSSColor;
import everyos.browser.spec.jcss.cssvalue.color.ColorValue;
import everyos.browser.spec.jcss.parser.CSSToken;

public final class BackgroundColorPropertyParser {
	
	private BackgroundColorPropertyParser() {}
	
	public static BackgroundColorProperty parse(CSSToken[] cssTokens) {
		ValueParseInfo<CSSColor> colorParseInfo = ColorValue.parse(0, cssTokens);
		
		if (colorParseInfo.failed() || colorParseInfo.getNumberConsumedTokens() != cssTokens.length) {
			return null;
		}
		
		return new BackgroundColorProperty(colorParseInfo.getValue().get());
	}
	
}
