package com.github.webicitybrowser.spiderhtml.misc;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;

public final class StackLogic {

	private StackLogic () {}

	public static void popUntil(ElementStack stack, String namespace, String name) {
		while (true) {
			Node node = stack.pop();
			if (
				node instanceof Element element &&
				element.getLocalTag().equals(name) &&
				element.getNamespace().equals(namespace)
			) {
				break;
			}
		}
	}
	
}
