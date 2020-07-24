package everyos.browser.webicity.webribbon.component;

import everyos.browser.webicity.renderer.html.dom.Element;
import everyos.browser.webicity.renderer.html.dom.Node;
import everyos.browser.webicity.renderer.html.dom.TextNode;

public final class WebComponentFactory {
	public static WebComponent createComponentFromNode(Node child) {
		if (child instanceof TextNode) {
			return new WebTextComponent((TextNode) child);
		} else if (child instanceof Element) {
			Element e = (Element) child;
			switch (e.localName) {
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
