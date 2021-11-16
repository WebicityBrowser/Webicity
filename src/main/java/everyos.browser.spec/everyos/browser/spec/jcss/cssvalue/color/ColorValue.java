package everyos.browser.spec.jcss.cssvalue.color;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.imp.Function;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.browser.spec.jcss.parser.IdentToken;
import everyos.engine.ribbon.core.graphics.paintfill.Color;

public class ColorValue {

	public static ValueParseInfo<Color> parse(int off, CSSToken[] cssTokens) {
		@SuppressWarnings("unchecked")
		ValueParseInfo<Color>[] results = new ValueParseInfo[4];
		
		//TODO: Absolute color base
		results[0] = HexColorValue.parse(off, cssTokens);
		results[1] = NamedColorValue.parse(off, cssTokens);
		results[2] = TransparentColorValue.parse(off, cssTokens);
		results[3] = RGBColorValue.parse(off, cssTokens);
		
		int highest = 0;
		int index = 0;
		
		for (int i = 0; i < results.length; i++) {
			if (results[i].getNumberConsumedTokens() > highest) {
				highest = results[i].getNumberConsumedTokens();
				index = i;
			}
		}
		
		if (highest == 0) {
			System.out.println("I failed you, Ben!");
			for (int i = 0; i < cssTokens.length; i++) {
				System.out.println(cssTokens[i]);
				if (cssTokens[i] instanceof IdentToken) {
					System.out.println(((IdentToken) cssTokens[i]).getValue());
				}
				if (cssTokens[i] instanceof Function) {
					System.out.println(((Function) cssTokens[i]).getName());
				}
			}
		}
		
		return results[index];
	}

}
