package everyos.parser.portalhtml.tokens;

public class CommentToken implements Token {

	private final StringBuilder value = new StringBuilder();
	
	public void appendToValue(int c) {
		value.append(c);
	}
	
	public String getValue() {
		return value.toString();
	}
	
}
