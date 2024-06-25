package com.github.webicitybrowser.spiderhtml.token;

public class DoctypeToken implements Token {

	private final StringBuilder nameBuilder = new StringBuilder(4);
	
	public void appendToName(int ch) {
		nameBuilder.appendCodePoint(ch);
	}

	public String getName() {
		return nameBuilder.toString();
	}
	
}
