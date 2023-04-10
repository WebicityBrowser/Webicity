package com.github.webicitybrowser.spec.dom.node.support.imp;

import java.util.Iterator;
import java.util.List;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.support.NodeList;

public class NodeListImp implements NodeList {

	private final List<Node> backingList;

	public NodeListImp(List<Node> backingList) {
		this.backingList = backingList;
	}
	
	@Override
	public Node get(int index) {
		return backingList.get(index);
	}
	
	@Override
	public int getLength() {
		return backingList.size();
	}

	@Override
	public Iterator<Node> iterator() {
		return backingList.iterator();
	}
	
}
