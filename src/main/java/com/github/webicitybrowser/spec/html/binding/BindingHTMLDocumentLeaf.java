package com.github.webicitybrowser.spec.html.binding;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentLeaf;

public class BindingHTMLDocumentLeaf extends BindingHTMLLeaf implements HTMLDocumentLeaf {

	public BindingHTMLDocumentLeaf(Document document) {
		super(null, document);
	}

	@Override
	public HTMLDocumentLeaf getNodeDocument() {
		return this;
	}

}
