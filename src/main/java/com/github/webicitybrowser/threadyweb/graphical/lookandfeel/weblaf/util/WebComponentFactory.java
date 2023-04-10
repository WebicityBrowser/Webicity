package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;

public final class WebComponentFactory {

	private WebComponentFactory() {};
	
	public static WebComponent createWebComponent(Node node) {
		if (node instanceof Element) {
			return ElementComponent.create((Element) node);
		} else {
			throw new RuntimeException("No web component available for " + node.getClass().getName());
		}
	}
	
}
