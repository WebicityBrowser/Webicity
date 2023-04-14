package com.github.webicitybrowser.spec.dom.node.imp.util;

import java.util.List;

import com.github.webicitybrowser.spec.dom.node.Node;

public final class InsertUtil {

	private InsertUtil() {}

	public static void insertBefore(List<Node> childNodes, Node node, Node child) {
		if (node == null) {
			childNodes.add(child);
		} else {
			int pos = childNodes.indexOf(node);
			assert pos != -1;
			childNodes.add(pos, child);
		}
	}
	
}
