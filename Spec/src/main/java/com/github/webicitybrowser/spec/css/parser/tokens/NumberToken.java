package com.github.webicitybrowser.spec.css.parser.tokens;

public record NumberToken(Number value, NumberTypeFlag typeFlag) implements Token {

	public NumberToken(Number value) {
		this(
			value,
			value instanceof Integer ? NumberTypeFlag.INTEGER : NumberTypeFlag.NUMBER);
	}
	
}
