package com.github.webicitybrowser.spec.html.parse;

import java.util.Map;

import com.github.webicitybrowser.spec.dom.node.Node;

public record ElementCreationOptions(
	String tagName, Map<String, String> attributes,
	String namespace, Node intendedParent
) {

}
