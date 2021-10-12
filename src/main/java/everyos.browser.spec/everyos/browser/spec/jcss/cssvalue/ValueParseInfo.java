package everyos.browser.spec.jcss.cssvalue;

import java.util.Optional;

public class ValueParseInfo<T> {
	
	private final T value;
	private final int numberConsumedTokens;

	public ValueParseInfo(T value, int consumedTokens) {
		this.value = value;
		this.numberConsumedTokens = consumedTokens;
	}
	
	public int getNumberConsumedTokens() {
		return this.numberConsumedTokens;
	}
	
	public Optional<T> getValue() {
		return Optional.ofNullable(this.value);
	}

	public boolean failed() {
		return this.value == null;
	}
	
	public static <T> ValueParseInfo<T> empty() {
		return new ValueParseInfo<T>(null, 0);
	}
}
