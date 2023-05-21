package com.github.webicitybrowser.spec.dom.node;

public interface Element extends Node {

	String getLocalName();

	String getNamespace();
	
	String[] getAttributeNames();

	void setAttribute(String name, String value);
	
	String getAttribute(String name);

	boolean hasAttribute(String name);

}
