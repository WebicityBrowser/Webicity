package com.github.webicitybrowser.spec.html.parse.tree;

public interface HTMLLeaf {

	void insertLeaf(int location, HTMLLeaf element);
	
	void appendLeaf(HTMLLeaf htmlLeaf);
	
	int getLength();

	HTMLDocumentLeaf getNodeDocument();
	
}
