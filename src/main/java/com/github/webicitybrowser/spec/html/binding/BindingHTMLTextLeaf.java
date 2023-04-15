package com.github.webicitybrowser.spec.html.binding;

import com.github.webicitybrowser.spec.dom.node.Text;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLTextLeaf;

public class BindingHTMLTextLeaf extends BindingHTMLLeaf implements HTMLTextLeaf {

	private final Text text;

	public BindingHTMLTextLeaf(HTMLDocumentLeaf nodeDocument, Text node) {
		super(nodeDocument, node);
		this.text = node;
	}

	@Override
	public void appendData(String string) {
		text.appendData(string);
	}

}
