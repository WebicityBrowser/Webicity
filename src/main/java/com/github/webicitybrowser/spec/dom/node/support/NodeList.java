package com.github.webicitybrowser.spec.dom.node.support;

import com.github.webicitybrowser.spec.dom.node.Node;

public interface NodeList extends Iterable<Node> {

	Node get(int index);

	int getLength();

}
