package everyos.browser.spec.jcss.parser;

public class NumberToken implements CSSToken {
	private Number value;

	public NumberToken(Number value, String type) {
		this.value = value;
	}
	
	public int getAsInt() {
		return this.value.intValue();
	}

	public float getAsFloat() {
		return this.value.floatValue();
	}
}
