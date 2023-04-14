package com.github.webicitybrowser.spec.dom.node.imp;

import java.util.List;

import com.github.webicitybrowser.spec.dom.exception.HierarchyRequestError;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.support.NodeList;
import com.github.webicitybrowser.spec.dom.node.support.imp.NodeListImp;

public class NodeImp implements Node {
	
	@Override
	public NodeList getChildNodes() {
		return new NodeListImp(List.of());
	}
	
	@Override
	public void insertBefore(Node rawBefore, Node rawChild) {
		throw new HierarchyRequestError();
	}

	@Override
	public void appendChild(Node node) {
		throw new HierarchyRequestError();
	}
	
	@Override
	public String toString() {
		return "[Node " + super.toString() + "]";
	}
	
}
