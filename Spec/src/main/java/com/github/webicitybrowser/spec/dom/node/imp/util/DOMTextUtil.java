package com.github.webicitybrowser.spec.dom.node.imp.util;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.Text;

public final class DOMTextUtil {

	private DOMTextUtil() {}
	
	public static String getChildTextContent(Node node) {
		StringBuilder textContent = new StringBuilder();
		for (Node childNode: node.getChildNodes()) {
			if (childNode instanceof Text text) {
				textContent.append(text.getData());
			}
		}
		
		return textContent.toString();
	}
	
}
