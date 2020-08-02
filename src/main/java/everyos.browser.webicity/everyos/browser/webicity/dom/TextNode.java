package everyos.browser.webicity.dom;

import everyos.browser.webicity.webribbon.component.WebTextComponent;

public class TextNode extends Node {
	public TextNode(String text) {
		this.wholeText = new StringBuilder(text);
		this.component = new WebTextComponent(this);
	}
	public TextNode() {
		this("");
	}
	
	public StringBuilder wholeText;
}
