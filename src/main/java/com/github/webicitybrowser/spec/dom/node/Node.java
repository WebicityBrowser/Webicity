package com.github.webicitybrowser.spec.dom.node;

import com.github.webicitybrowser.spec.dom.node.support.NodeList;

public interface Node {

	NodeList getChildNodes();
	
	void appendChild(Node node);
	
}
