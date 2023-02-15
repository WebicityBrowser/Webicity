package everyos.web.spec.css.parser.property;

import everyos.web.spec.css.parser.property.imp.PropertyValueParseResultImp;

public final class PropertyValueParserUtil {

	private PropertyValueParserUtil() {}
	
	@SafeVarargs
	public static <T> PropertyValueParseResult<T> takeLongestResult(PropertyValueParseResult<T>... results) {
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
