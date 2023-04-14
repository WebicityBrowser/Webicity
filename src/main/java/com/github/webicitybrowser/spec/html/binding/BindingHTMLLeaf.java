package com.github.webicitybrowser.spec.html.binding;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLLeaf;

public class BindingHTMLLeaf implements HTMLLeaf {
	
	private final HTMLDocumentLeaf nodeDocument;
	private final Node node;

	public BindingHTMLLeaf(HTMLDocumentLeaf nodeDocument, Node node) {
		this.nodeDocument = nodeDocument;
		this.node = node;
	}

	@Override
	public void insertBeforeLeaf(HTMLLeaf node, HTMLLeaf child) {
		if (node == null) {
			appendLeaf(child);
			return;
		}
		Node rawBefore = ((BindingHTMLLeaf) node).getRaw();
		Node rawChild = ((BindingHTMLLeaf) child).getRaw();
		this.node.insertBefore(rawBefore, rawChild);
	}

	@Override
	public void appendLeaf(HTMLLeaf htmlLeaf) {
		Node rawNode = ((BindingHTMLLeaf) htmlLeaf).getRaw();
		node.appendChild(rawNode);
	}

	@Override
	public int getLength() {
		return node.getChildNodes().getLength();
	}

	@Override
	public HTMLDocumentLeaf getNodeDocument() {
		return this.nodeDocument;
	}
	
	private Node getRaw() {
		return this.node;
	}

}
