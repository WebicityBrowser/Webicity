package everyos.browser.spec.jcss.cssvalue.color;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.browser.spec.jcss.parser.IdentToken;

public class TransparentColorValue {
	
	public static ValueParseInfo<CSSColor> parse(int off, CSSToken[] cssTokens) {
		if (!(cssTokens[off] instanceof IdentToken && ((IdentToken) cssTokens[off]).getValue().equals("transparent"))) {
			return ValueParseInfo.<CSSColor>empty();
		}
		
		return new ValueParseInfo<CSSColor>(CSSColor.TRANSPARENT, 1);
	}
	
}
