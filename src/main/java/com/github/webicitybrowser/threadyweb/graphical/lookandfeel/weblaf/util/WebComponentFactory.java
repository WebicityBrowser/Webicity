package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util;

import com.github.webicitybrowser.spec.dom.node.Comment;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.Text;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.threadyweb.tree.BreakComponent;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;
import com.github.webicitybrowser.threadyweb.tree.imp.TextComponentImp;

public final class WebComponentFactory {

	private WebComponentFactory() {};
	
	public static WebComponent createWebComponent(Node node) {
		if (node instanceof Element element) {
			if (element.getLocalName().equals("br") && element.getNamespace().equals(Namespace.HTML_NAMESPACE)) {
				return BreakComponent.create(element);
			}

			return ElementComponent.create(element);
		} else if (node instanceof Text text) {
			return new TextComponentImp(text);
		} else if (node instanceof Comment) {
			return null;
		} else {
			throw new RuntimeException("No web component available for " + node.getClass().getName());
		}
	}
	
}
