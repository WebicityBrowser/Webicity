package com.github.webicitybrowser.spec.css.parser.tokens;

public record DimensionToken(Number value, NumberTypeFlag typeFlag, String unit) implements Token {

	public DimensionToken(Number value, String unit) {
		this(
			value,
			value instanceof Integer ? NumberTypeFlag.INTEGER : NumberTypeFlag.NUMBER,
			unit);
	}
	
}
