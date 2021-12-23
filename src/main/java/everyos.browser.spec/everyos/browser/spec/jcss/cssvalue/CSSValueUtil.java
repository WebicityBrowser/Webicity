package everyos.browser.spec.jcss.cssvalue;

public final class CSSValueUtil {

	private CSSValueUtil() {}
	
	//TODO: Not necessarily safe varargs, but without this there are warnings everywhere
	@SafeVarargs
	public static <T> ValueParseInfo<T> takeLongestValue(ValueParseInfo<T>... values) {
		int highest = 0;
		int index = 0;
		
		for (int i = 0; i < values.length; i++) {
			if (values[i].getNumberConsumedTokens() > highest) {
				highest = values[i].getNumberConsumedTokens();
				index = i;
			}
		}
		
		return values[index];
	}
	
}
