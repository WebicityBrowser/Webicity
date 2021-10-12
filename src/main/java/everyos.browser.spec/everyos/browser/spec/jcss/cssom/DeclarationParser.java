package everyos.browser.spec.jcss.cssom;

import everyos.browser.spec.jcss.cssom.property.Property;
import everyos.browser.spec.jcss.cssom.property.backgroundcolor.BackgroundColorPropertyParser;
import everyos.browser.spec.jcss.cssom.property.color.ColorPropertyParser;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.browser.spec.jcss.parser.Declaration;
import everyos.browser.spec.jcss.parser.WhitespaceToken;

public final class DeclarationParser {

	public static Property getPropertyFor(Declaration rule) {
		CSSToken[] tidyValue = tidy(rule.getValue());
		
		//TODO: Default parsing for default things like var/inherit/etc
		
		switch(rule.getName()) {
			case "color":
				return ColorPropertyParser.parse(tidyValue);
				
			case "background-color":
				return BackgroundColorPropertyParser.parse(tidyValue);
		
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
