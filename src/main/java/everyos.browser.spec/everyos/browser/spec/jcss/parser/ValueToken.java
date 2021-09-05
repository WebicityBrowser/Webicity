package everyos.browser.spec.jcss.parser;

public class ValueToken implements CSSToken {
	private TokenType type = TokenType.UNRESTRICTED;
	private String value = "";
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setType(TokenType type) {
		this.type = type;
	}
	
	public TokenType getType() {
		return type;
	}
}
