package com.github.webicitybrowser.spec.dom.node.imp.util;

import java.util.List;

import com.github.webicitybrowser.spec.dom.node.Node;

public final class StringifyUtil {

	private StringifyUtil() {}
	
	public static String serializeChildren(List<Node> children, String indentation) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Node child: children) {
			stringBuilder.append("\n");
			stringBuilder.append(indent(child.toString(), indentation));
		}
		if (stringBuilder.isEmpty()) {
			return "";
		}
		return stringBuilder.substring(1);
	}

	private static String indent(String string, String indentation) {
		return indentation + string.replaceAll("\n", "\n" + indentation);
	}
	
}
