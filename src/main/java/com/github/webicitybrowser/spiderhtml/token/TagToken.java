package com.github.webicitybrowser.spiderhtml.token;

public abstract class TagToken implements Token {

	private final StringBuilder nameBuilder;

	public TagToken(String name) {
		this.nameBuilder = new StringBuilder(name);
	}
	
	public String getName() {
		return nameBuilder.toString();
	}

	public void appendToName(int ch) {
		nameBuilder.appendCodePoint(ch);
	}
	
}
