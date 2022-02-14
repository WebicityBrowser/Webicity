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
		CSSToken[] trimmedValue = removeExtraWhitespace(rule.getValue());
		
		//TODO: Default parsing for default things like var/inherit/etc
		
		//TODO: Make this a HashMap?
		switch(rule.getName()) {
			case "color":
				return ColorPropertyParser.parse(trimmedValue);
				
			case "background-color":
			case "background": //Temporary case statement
				return BackgroundColorPropertyParser.parse(trimmedValue);
				
			case "display":
				return DisplayPropertyParser.parse(trimmedValue);
				
			case "width":
				return WidthPropertyParser.parse(trimmedValue);
				
			case "height":
				return HeightPropertyParser.parse(trimmedValue);
				
			case "font-size":
				return FontSizePropertyParser.parse(trimmedValue);
		
			default:
				return null;
		}
	}

	private static CSSToken[] removeExtraWhitespace(CSSToken[] value) {
		int searchPoint = 0;
		int len = 0;
		boolean ignoreWhitespace = true;
		
		for (int insertionPoint = 0; searchPoint < value.length;) {
			if (value[searchPoint] instanceof WhitespaceToken && ignoreWhitespace) {
				// We only allow one whitespace character in a row
				searchPoint++;
			} else {
				ignoreWhitespace = value[searchPoint] instanceof WhitespaceToken;
				//This if statement exists so that we don't include whitespace in the
				// tail end of the the array 
				if (!ignoreWhitespace) {
					len = insertionPoint + 1;
				}
				
				value[insertionPoint++] = value[searchPoint++];
			}
		}
		
		CSSToken[] tidy = new CSSToken[len];
		System.arraycopy(value, 0, tidy, 0, len);
		
		return tidy;
	}

}
