package everyos.browser.webicity.webribbon.core.component;

import everyos.browser.spec.javadom.intf.Element;
import everyos.browser.spec.javadom.intf.Node;
import everyos.browser.spec.javadom.intf.Text;
import everyos.browser.spec.jhtml.intf.HTMLTitleElement;
import everyos.browser.webicity.renderer.html.HTMLRenderer;

public final class WebComponentFactory {
	private WebComponentFactory() {}
	
	public static WebComponent createComponentFromNode(Node child, HTMLRenderer renderer) {
		if (child instanceof Text) {
			return new WebTextComponent(renderer, (Text) child);
		} else if (child instanceof Element) {
			//TODO: Use a factory instead?
			Element e = (Element) child;
			if (child instanceof HTMLTitleElement) {
				return new WebTitleComponent(renderer, e);
			}
			switch (e.getTagName()) {
				case "a":
					return new WebAnchorComponent(renderer, e);
				case "br":
					return new WebBreakComponent(renderer, e);
				case "style":
					return new WebStyleComponent(renderer, e);
				case "script":
					return new WebScriptComponent(renderer, e);
				case "div":
				case "p":
				case "li":
				case "nav":
					return new WebDivComponent(renderer, e);
				default:
					return new WebComponent(renderer, e);
			}
		} else {
			return null;
		}
	}
}
