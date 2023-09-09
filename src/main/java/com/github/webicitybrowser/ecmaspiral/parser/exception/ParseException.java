package com.github.webicitybrowser.ecmaspiral.parser.exception;

import com.github.webicitybrowser.ecmaspiral.parser.tokens.Token.TokenMeta;

public class ParseException extends Exception {
	
	private final TokenMeta locationInfo;

	public ParseException(String message, TokenMeta locationInfo) {
		super(message);
		this.locationInfo = locationInfo;
	}

	public TokenMeta getLocationInfo() {
		return locationInfo;
	}

}
