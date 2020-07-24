package everyos.browser.webicity.renderer.html.dom;

public class TextNode extends Node {
	public TextNode(String text) {
		this.wholeText = new StringBuilder(text);
	}
	public TextNode() {
		this("");
	}
	
	public StringBuilder wholeText;
}
