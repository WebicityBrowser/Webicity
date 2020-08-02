package everyos.browser.webicity.renderer.html.dom.impl;

public class CommentImpl extends NodeImpl {
	@SuppressWarnings("unused")
	private String data;

	public CommentImpl(DocumentImpl nodeDocument, String data) {
		super(nodeDocument);
		this.data = data;
	}
}
