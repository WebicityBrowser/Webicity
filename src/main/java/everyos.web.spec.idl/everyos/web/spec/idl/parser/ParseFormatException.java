package everyos.web.spec.idl.parser;

public class ParseFormatException extends Exception {

	private static final long serialVersionUID = 5693865001093843928L;
	
	private final int tokenPosition;
	
	public ParseFormatException(String message, int tokenPosition) {
		super(message);
		this.tokenPosition = tokenPosition;
	}

	int getTokenPosition() {
		return tokenPosition;
	};
	
}
