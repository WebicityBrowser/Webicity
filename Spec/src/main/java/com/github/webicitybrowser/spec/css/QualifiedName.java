package com.github.webicitybrowser.spec.css;

public interface QualifiedName {
	
	public static final String NO_NAMESPACE = "";
	public static final String ANY_NAMESPACE = "*";
	public static final String DEFAULT_NAMESPACE = "**";

	String getNamespace();
	
	String getName();
	
}
