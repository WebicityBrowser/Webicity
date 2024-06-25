package com.github.webicitybrowser.spiderhtml.token;

public class CommentToken implements Token {

	private final StringBuilder dataBuilder;

	public CommentToken(String data) {
		this.dataBuilder = new StringBuilder(data);
	}

	public void appendToData(int ch) {
		dataBuilder.appendCodePoint(ch);
	}

	public String getData() {
		return dataBuilder.toString();
	}
	
}
