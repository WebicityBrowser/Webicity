package everyos.browser.webicity.lacewebextensions.core.component.imp;

import everyos.browser.spec.javadom.intf.Element;
import everyos.browser.spec.javadom.intf.Node;
import everyos.browser.spec.javadom.intf.Text;
import everyos.browser.webicity.lacewebextensions.core.component.WebAnchorComponent;
import everyos.browser.webicity.lacewebextensions.core.component.WebBreakComponent;
import everyos.browser.webicity.lacewebextensions.core.component.WebComponent;
import everyos.browser.webicity.lacewebextensions.core.component.WebDivComponent;
import everyos.browser.webicity.lacewebextensions.core.component.WebTextComponent;

public class WebComponentFactory {

	public WebComponent createComponentFor(Node node) {
		//TODO: Obey "is" attribute
		if (node instanceof Text) {
			return new WebTextComponent((Text) node);
		} else if (node instanceof Element) {
			//TODO: Use a factory instead?
			Element e = (Element) node;
			switch (e.getTagName()) {
				case "a":
					return new WebAnchorComponent(e);
				case "br":
					return new WebBreakComponent(e);
				case "style":
				case "script":
				case "title":
					return null;
				case "div":
				case "p":
				case "li":
				case "nav":
					return new WebDivComponent(e);
				default:
					return new WebComponent(e);
			}
		} else {
			return null;
		}
	}

}
