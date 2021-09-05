package everyos.browser.spec.jhtml.parser;

public class CommentToken extends Token {
	private StringBuilder dataBuilder;

	public CommentToken(String string) {
		this.dataBuilder = new StringBuilder(string);
	}
	
	public StringBuilder getDataBuilder() {
		return dataBuilder;
	}
}
