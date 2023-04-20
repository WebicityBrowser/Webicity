package com.github.webicitybrowser.spec.css.parser;

public class ParseFormatException extends Exception {

	private static final long serialVersionUID = 5091292412333339606L;
	
	private final int tokenPosition;
	
	public ParseFormatException(String message, int tokenPosition) {
		super(message);
		this.tokenPosition = tokenPosition;
	}

	int getTokenPosition() {
		return tokenPosition;
	};
	
}
