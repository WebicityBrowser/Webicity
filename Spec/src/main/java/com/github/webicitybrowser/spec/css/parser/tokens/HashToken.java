package com.github.webicitybrowser.spec.css.parser.tokens;

public record HashToken(String value, HashTypeFlag typeFlag) implements Token {

	public static enum HashTypeFlag {
		ID, UNRESTRICTED
	}
	
}
