package everyos.browser.webicity.dom;

import everyos.browser.webicity.webribbon.component.WebComponent;

public class Document extends Node {
	//Sidenote: https://github.com/whatwg/html/pull/3917 is a thing
	public static enum DocumentReadyState {LOADING, INTERACTIVE, COMPLETE}
	
	public Document() {
		this.nodeDocument = this;
		
		this.component = new WebComponent(this);
	}
}
