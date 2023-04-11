package com.github.webicitybrowser.spiderhtml.token;

public class StartTagToken extends TagToken {

	private final StringBuilder nameBuilder;

	public StartTagToken(String name) {
		this.nameBuilder = new StringBuilder(name);
	}
	
	public String getName() {
		return nameBuilder.toString();
	}
	
}
