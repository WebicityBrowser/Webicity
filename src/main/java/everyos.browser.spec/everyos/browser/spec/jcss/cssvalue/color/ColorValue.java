package everyos.browser.spec.jcss.cssvalue.color;

import everyos.browser.spec.jcss.cssvalue.CSSValueUtil;
import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.engine.ribbon.core.graphics.paintfill.Color;

public class ColorValue {

	public static ValueParseInfo<Color> parse(int off, CSSToken[] cssTokens) {
		//TODO: Absolute color base
		return CSSValueUtil.takeLongestValue(
			HexColorValue.parse(off, cssTokens),
			NamedColorValue.parse(off, cssTokens),
			TransparentColorValue.parse(off, cssTokens),
			RGBColorValue.parse(off, cssTokens));
	}

}
