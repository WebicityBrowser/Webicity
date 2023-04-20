package com.github.webicitybrowser.spec.css.parser.tokens;

public interface HashToken extends Token {

	String getValue();
	
	HashTypeFlag getTypeFlag();
	
	public static enum HashTypeFlag {
		ID, UNRESTRICTED
	}
	
}
