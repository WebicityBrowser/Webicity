package everyos.browser.spec.jcss.cssom;

import everyos.browser.spec.jcss.cssom.property.Property;
import everyos.browser.spec.jcss.cssom.property.backgroundcolor.BackgroundColorPropertyParser;
import everyos.browser.spec.jcss.cssom.property.color.ColorPropertyParser;
import everyos.browser.spec.jcss.cssom.property.display.DisplayPropertyParser;
import everyos.browser.spec.jcss.cssom.property.fontsize.FontSizePropertyParser;
import everyos.browser.spec.jcss.cssom.property.height.HeightPropertyParser;
import everyos.browser.spec.jcss.cssom.property.width.WidthPropertyParser;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.browser.spec.jcss.parser.Declaration;
import everyos.browser.spec.jcss.parser.WhitespaceToken;

public final class DeclarationParser {

	public static Property getPropertyFor(Declaration rule) {
		CSSToken[] tidyValue = tidy(rule.getValue());
		
		//TODO: Default parsing for default things like var/inherit/etc
		
		//TODO: Make this a HashMap?
		switch(rule.getName()) {
			case "color":
				return ColorPropertyParser.parse(tidyValue);
				
			case "background-color":
			case "background": //Temporary case statement
				return BackgroundColorPropertyParser.parse(tidyValue);
				
			case "display":
				return DisplayPropertyParser.parse(tidyValue);
				
			case "width":
				return WidthPropertyParser.parse(tidyValue);
				
			case "height":
				return HeightPropertyParser.parse(tidyValue);
				
			case "font-size":
				return FontSizePropertyParser.parse(tidyValue);
		
			default:
				return null;
		}
	}

	private static CSSToken[] tidy(CSSToken[] value) {
		int j = 0;
		int len = 0;
		boolean ignoreWhitespace = true;
		
		for (int i = 0; j < value.length;) {
			if (value[j] instanceof WhitespaceToken && ignoreWhitespace) {
				j++;
			} else {
				ignoreWhitespace = value[j] instanceof WhitespaceToken;
				if (!ignoreWhitespace) {
					len = i + 1;
				}
				
				value[i++] = value[j++];
			}
		}
		
		CSSToken[] tidy = new CSSToken[len];
		System.arraycopy(value, 0, tidy, 0, len);
		
		return tidy;
	}

}
