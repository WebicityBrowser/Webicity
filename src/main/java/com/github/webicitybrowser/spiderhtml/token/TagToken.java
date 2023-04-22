package com.github.webicitybrowser.spiderhtml.token;

public abstract class TagToken implements Token {

	private final StringBuilder nameBuilder;
	private boolean selfClosingTag;
	private boolean acknowledgedSelfClosingTag;

	public TagToken(String name) {
		this.nameBuilder = new StringBuilder(name);
	}
	
	public String getName() {
		return nameBuilder.toString();
	}

	public void appendToName(int ch) {
		nameBuilder.appendCodePoint(ch);
	}
	
	public void setSelfClosingTag(boolean selfClosingTag) {
		this.selfClosingTag = selfClosingTag;
	}
	
	public boolean isSelfClosingTag() {
		return this.selfClosingTag;
	}
	
	public void acknowledgeSelfClosingTag() {
		this.acknowledgedSelfClosingTag = true;
	}
	
	public boolean hasAcknowledgedSelfClosingTag() {
		return this.acknowledgedSelfClosingTag;
	}
	
}
