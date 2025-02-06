package com.github.webicitybrowser.spec.css;

public record QualifiedName(String namespace, String name) {
	
	public static final String NO_NAMESPACE = "";
	public static final String ANY_NAMESPACE = "*";
	public static final String DEFAULT_NAMESPACE = "**";

	public static final String ANY_NAME = "*";

	public static QualifiedName create(String namespace, String name) {
		return new QualifiedName(namespace.toLowerCase(), name.toLowerCase());
	}
	
}
