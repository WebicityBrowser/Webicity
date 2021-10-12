package everyos.browser.spec.jcss.parser;

public class DelimToken extends ValueToken {
	public DelimToken(String value) {
		super.setValue(value);
	}
	
	public DelimToken(int value) {
		super.setValue(new String(new int[] {value}, 0, 1));
	}
}
