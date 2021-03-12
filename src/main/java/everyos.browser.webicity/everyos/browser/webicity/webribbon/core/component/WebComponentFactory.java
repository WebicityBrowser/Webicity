package everyos.browser.webicity.webribbon.core.component;

import everyos.browser.javadom.intf.Element;
import everyos.browser.javadom.intf.Node;
import everyos.browser.javadom.intf.Text;
import everyos.browser.jhtml.intf.HTMLTitleElement;

public final class WebComponentFactory {
	private WebComponentFactory() {}
	
	public static WebComponent createComponentFromNode(Node child) {
		if (child instanceof Text) {
			return new WebTextComponent((Text) child);
		} else if (child instanceof Element) {
			//TODO: Use a reflective factory instead?
			Element e = (Element) child;
			if (HTMLTitleElement.class.isAssignableFrom(child.getClass())) {
				return new WebTitleComponent(e);
			}
			switch (e.getTagName()) {
				case "a":
					return new WebAnchorComponent(e);
				case "br":
					return new WebBreakComponent(e);
				case "style":
					return new WebStyleComponent(e);
				case "script":
					return new WebScriptComponent(e);
				default:
					return new WebComponent(e);
			}
		} else {
			return null;
		}
	}
}
