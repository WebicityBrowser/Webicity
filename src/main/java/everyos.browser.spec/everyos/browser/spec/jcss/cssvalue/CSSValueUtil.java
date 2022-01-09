package everyos.browser.spec.jcss.cssvalue;

public final class CSSValueUtil {

	private CSSValueUtil() {}
	
	//TODO: If we make a subclass of Sizing called ZeroSizing, this could be a potential place to ensure that
	// <number> takes priority over <length> for 0. Preferably, though, a better approach is found
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
