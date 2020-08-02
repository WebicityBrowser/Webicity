package everyos.browser.webicity.dom;

import everyos.browser.webicity.webribbon.component.WebAComponent;
import everyos.browser.webicity.webribbon.component.WebBreakComponent;
import everyos.browser.webicity.webribbon.component.WebComponent;

public class Element extends Node {
	public String namespaceURI;
	public String prefix;
	public String localName;
	public String tagName;
	
	public String is;
	
	public WebComponent component() {
		if (this.component == null) {
			switch(tagName.toString()) {
				case "br":
					this.component = new WebBreakComponent(this);
					//And ironically:
					break;
				case "a":
					this.component = new WebAComponent(this);
					break;
				default:
					this.component = new WebComponent(this);
			}
		}
		return this.component;
	}
}
