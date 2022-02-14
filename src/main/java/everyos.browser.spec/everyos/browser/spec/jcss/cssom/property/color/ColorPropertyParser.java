package everyos.browser.spec.jcss.cssom.property.color;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.cssvalue.color.CSSColor;
import everyos.browser.spec.jcss.cssvalue.color.ColorValue;
import everyos.browser.spec.jcss.parser.CSSToken;

public final class ColorPropertyParser {
	
	private ColorPropertyParser() {}
	
	public static ColorProperty parse(CSSToken[] cssTokens) {
		ValueParseInfo<CSSColor> colorParseInfo = ColorValue.parse(0, cssTokens);
		
		if (colorParseInfo.failed() || colorParseInfo.getNumberConsumedTokens() != cssTokens.length) {
			return null;
		}
		
		return new ColorProperty(colorParseInfo.getValue().get());
	}
	
}
