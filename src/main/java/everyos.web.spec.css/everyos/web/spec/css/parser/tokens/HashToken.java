package everyos.web.spec.css.parser.tokens;

public interface HashToken extends Token {

	String getValue();
	
	TypeFlag getTypeFlag();
	
	public static enum TypeFlag {
		ID, UNRESTRICTED
	}
	
}
