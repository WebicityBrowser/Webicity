package everyos.browser.spec.jcss.cssvalue.color;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.browser.spec.jcss.parser.IdentToken;
import everyos.engine.ribbon.core.graphics.Color;

public class TransparentColorValue {
	
	public static ValueParseInfo<Color> parse(int off, CSSToken[] cssTokens) {
		if (!(cssTokens[off] instanceof IdentToken && ((IdentToken) cssTokens[off]).getValue().equals("transparent"))) {
			return ValueParseInfo.<Color>empty();
		}
		
		return new ValueParseInfo<Color>(new Color(0, 0, 0, 0), 1);
	}
	
}
