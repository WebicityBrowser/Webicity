package com.github.webicitybrowser.spec.html.parse;

import java.util.Map;

import com.github.webicitybrowser.spec.html.parse.tree.HTMLLeaf;

public record ElementCreationOptions(
	String tagName, Map<String, String> attributes,
	String namespace, HTMLLeaf intendedParent
) {

}
