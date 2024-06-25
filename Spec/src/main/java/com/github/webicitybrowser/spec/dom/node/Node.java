package com.github.webicitybrowser.spec.dom.node;

import com.github.webicitybrowser.spec.dom.node.support.NodeList;

public interface Node {

	Node getOwnerDocument();
	
	Node getParentNode();
	
	NodeList getChildNodes();
	
	Node getPreviousSibling();
	
	Node getLastChild();
	
	//
	
	void insertBefore(Node rawBefore, Node rawChild);
	
	void appendChild(Node node);
	
}
