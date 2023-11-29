package com.github.webicitybrowser.spec.css;

/**
 * A qualified name is a name that is qualified by a namespace.
 * A name in a different namespace is considered to be a different name.
 */
public interface QualifiedName {
	
	public static final String NO_NAMESPACE = "";
	public static final String ANY_NAMESPACE = "*";
	public static final String DEFAULT_NAMESPACE = "**";

	String getNamespace();
	
	String getName();
	
}
