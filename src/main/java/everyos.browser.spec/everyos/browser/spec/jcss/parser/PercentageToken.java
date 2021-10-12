package everyos.browser.spec.jcss.parser;

public class PercentageToken implements CSSToken {
	private Number value;

	public PercentageToken(Number value) {
		this.value = value;
	}
	
	public float getAsFloat() {
		return this.value.floatValue() / 100;
	}
}
