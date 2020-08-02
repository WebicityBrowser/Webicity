package everyos.browser.webicity.renderer.html.dom.impl;

public class TextNodeImpl extends NodeImpl {
	public TextNodeImpl(DocumentImpl nodeDocument, String text) {
		super(nodeDocument);
		this.wholeText = new StringBuilder(text);
	}
	public TextNodeImpl(DocumentImpl nodeDocument) {
		this(nodeDocument, "");
	}
	
	public StringBuilder wholeText;
}
