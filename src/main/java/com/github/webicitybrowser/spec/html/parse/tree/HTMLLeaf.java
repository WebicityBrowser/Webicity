package com.github.webicitybrowser.spec.html.parse.tree;

public interface HTMLLeaf {

	void insertBeforeLeaf(HTMLLeaf node, HTMLLeaf child);
	
	void appendLeaf(HTMLLeaf htmlLeaf);
	
	int getLength();

	HTMLDocumentLeaf getNodeDocument();
	
}
