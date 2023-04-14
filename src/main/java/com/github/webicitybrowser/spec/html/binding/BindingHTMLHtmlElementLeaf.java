package com.github.webicitybrowser.spec.html.binding;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLHtmlElementLeaf;

public class BindingHTMLHtmlElementLeaf extends BindingHTMLElementLeaf implements HTMLHtmlElementLeaf {

	public BindingHTMLHtmlElementLeaf(HTMLDocumentLeaf nodeDocument, Node node) {
		super(nodeDocument, node);
	}

}
