package everyos.browser.webicity.webribbon.core.component;

import everyos.browser.javadom.intf.Element;
import everyos.browser.javadom.intf.Node;
import everyos.browser.javadom.intf.Text;

public final class WebComponentFactory {
	private WebComponentFactory() {}
	
	public static WebComponent createComponentFromNode(Node child) {
		if (child instanceof Text) {
			return new WebTextComponent((Text) child);
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
