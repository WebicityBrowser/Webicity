package com.github.webicitybrowser.spec.css.parser.tokens;

public interface NumberToken extends Token {

	Number getValue();

	NumberTypeFlag getTypeFlag();
	
}
