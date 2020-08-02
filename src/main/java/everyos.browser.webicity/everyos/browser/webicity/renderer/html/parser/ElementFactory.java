package everyos.browser.webicity.renderer.html.parser;

import everyos.browser.webicity.renderer.html.dom.Element;
import everyos.browser.webicity.renderer.html.dom.impl.DocumentImpl;
import everyos.browser.webicity.renderer.html.dom.impl.ElementImpl;
import everyos.browser.webicity.renderer.html.dom.impl.html.HTMLAnchorElementImpl;
import everyos.browser.webicity.renderer.html.dom.impl.html.HTMLBRElementImpl;
import everyos.browser.webicity.renderer.html.dom.impl.html.HTMLElementImpl;

public class ElementFactory {
	public static Element element(String namespace, String localName, DocumentImpl nodeDocument) {
		if (!namespace.equals(HTMLParser.HTML_NAMESPACE)) return new ElementImpl(nodeDocument);
		switch (localName) {
			case "a":
				return new HTMLAnchorElementImpl(nodeDocument);
			case "break":
				return new HTMLBRElementImpl(nodeDocument);
		}
		return new HTMLElementImpl(nodeDocument);
	}
}
