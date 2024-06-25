package com.github.webicitybrowser.spec.css.parser.property;

import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.property.CSSValue;

public final class PropertyValueParserUtil {

	private PropertyValueParserUtil() {}
	
	@SafeVarargs
	public static <T extends CSSValue> PropertyValueParseResult<T> takeLongestResult(PropertyValueParseResult<T>... results) {
		PropertyValueParseResult<T> longestResult = PropertyValueParseResultImp.empty();
		int highestLength = 0;
		
		for (PropertyValueParseResult<T> result: results) {
			if (result.getResult() != null && result.getLength() > highestLength) {
				highestLength = result.getLength();
				longestResult = result;
			}
		}
		
		return longestResult;
	}
	
}
