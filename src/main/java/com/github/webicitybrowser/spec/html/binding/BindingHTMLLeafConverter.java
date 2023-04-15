package com.github.webicitybrowser.spec.html.binding;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.DocumentType;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.Text;
import com.github.webicitybrowser.spec.html.node.HTMLBodyElement;
import com.github.webicitybrowser.spec.html.node.HTMLHeadElement;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLLeaf;

public final class BindingHTMLLeafConverter {

	private BindingHTMLLeafConverter() {}
	
	public static HTMLLeaf createLeafFor(HTMLDocumentLeaf nodeDocument, Node node) {
		if (node == null) {
			return null;
		} else if (node instanceof HTMLHeadElement) {
			return new BindingHTMLHtmlElementLeaf(nodeDocument, node);
		} else if (node instanceof HTMLBodyElement) {
			return new BindingHTMLElementLeaf(nodeDocument, node);
		} else if (node instanceof DocumentType) {
			return new BindingHTMLDocumentTypeLeaf(nodeDocument, node);
		} else if (node instanceof Document) {
			// TODO: Make sure they are the same document
			return nodeDocument;
		} else if (node instanceof Text text) {
			return new BindingHTMLTextLeaf(nodeDocument, text);
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
}
