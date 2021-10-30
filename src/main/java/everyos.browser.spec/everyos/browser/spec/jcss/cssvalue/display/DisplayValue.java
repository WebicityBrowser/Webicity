package everyos.browser.spec.jcss.cssvalue.display;

import everyos.browser.spec.jcss.cssom.property.display.DisplayProperty;
import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.parser.CSSToken;

public class DisplayValue {

	public static ValueParseInfo<DisplayProperty> parse(int off, CSSToken[] cssTokens) {
		@SuppressWarnings("unchecked")
		ValueParseInfo<DisplayProperty>[] results = new ValueParseInfo[1];
		
		//TODO: Absolute color base
		results[0] = LegacyDisplayValue.parse(off, cssTokens);
		
		int highest = 0;
		int index = 0;
		
		for (int i = 0; i < results.length; i++) {
			if (results[i].getNumberConsumedTokens() > highest) {
				highest = results[i].getNumberConsumedTokens();
				index = i;
			}
		}
		
		return results[index];
	}
	
}
