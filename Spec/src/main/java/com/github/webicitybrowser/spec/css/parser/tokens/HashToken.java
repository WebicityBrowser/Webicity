package com.github.webicitybrowser.spec.css.parser.tokens;

public interface HashToken extends Token {

	String getValue();
	
	HashTypeFlag getTypeFlag();
	
	public static enum HashTypeFlag {
		ID, UNRESTRICTED
	}

	public static HashToken create(String value, HashTypeFlag flag) {
		return new HashToken() {
			@Override
			public String getValue() {
				return value;
			}

			@Override
			public HashTypeFlag getTypeFlag() {
				return flag;
			}
		};
	}
	
}
