package com.github.webicitybrowser.spec.htmlbrowsers;

public record TupleOrigin(String scheme, String host, int port, String domain) implements Origin {
	
}
