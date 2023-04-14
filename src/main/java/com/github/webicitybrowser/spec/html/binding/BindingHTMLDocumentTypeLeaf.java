package com.github.webicitybrowser.spec.html.binding;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentTypeLeaf;

public class BindingHTMLDocumentTypeLeaf extends BindingHTMLLeaf implements HTMLDocumentTypeLeaf {

	public BindingHTMLDocumentTypeLeaf(HTMLDocumentLeaf nodeDocument, Node node) {
		super(nodeDocument, node);
	}

}
