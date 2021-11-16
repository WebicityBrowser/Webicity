package everyos.browser.spec.jcss.cssom.property.color;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.cssvalue.color.ColorValue;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.engine.ribbon.core.graphics.paintfill.Color;

public final class ColorPropertyParser {
	
	private ColorPropertyParser() {}
	
	public static ColorProperty parse(CSSToken[] cssTokens) {
		ValueParseInfo<Color> colorParseInfo = ColorValue.parse(0, cssTokens);
		
		if (colorParseInfo.failed() || colorParseInfo.getNumberConsumedTokens() != cssTokens.length) {
			return null;
		}
		
		return new ColorProperty(colorParseInfo.getValue().get());
	}
}
