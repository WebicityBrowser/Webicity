package com.github.webicitybrowser.spec.html.binding;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLElementLeaf;

public class BindingHTMLElementLeaf extends BindingHTMLLeaf implements HTMLElementLeaf {

	public BindingHTMLElementLeaf(HTMLDocumentLeaf nodeDocument, Node node) {
		super(nodeDocument, node);
	}

}
