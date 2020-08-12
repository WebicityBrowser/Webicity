package everyos.browser.webicity.webribbon.core.component;

import everyos.browser.webicity.renderer.html.dom.Element;
import everyos.browser.webicity.renderer.html.dom.Node;
import everyos.browser.webicity.renderer.html.dom.impl.TextNodeImpl;

public final class WebComponentFactory {
	private WebComponentFactory() {}
	
	public static WebComponent createComponentFromNode(Node child) {
		if (child instanceof TextNodeImpl) {
			return new WebTextComponent((TextNodeImpl) child);
		} else if (child instanceof Element) {
			Element e = (Element) child;
			switch (e.getLocalName()) {
				case "a":
					return new WebAnchorComponent(e);
				case "br":
					return new WebBreakComponent(e);
				default:
					return new WebComponent(e);
			}
		} else {
			return null;
		}
	}
}
