package com.github.webicitybrowser.spec.dom.node.imp;

import java.util.List;

import com.github.webicitybrowser.spec.dom.exception.HierarchyRequestError;
import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.support.NodeList;
import com.github.webicitybrowser.spec.dom.node.support.imp.NodeListImp;

public abstract class NodeImp implements Node {
	
	private static final NodeList EMPTY_NODE_LIST = new NodeListImp(List.of());
	
	private final Document nodeDocument;
	private Node parentNode;
	
	public NodeImp(Document nodeDocument) {
		this.nodeDocument = nodeDocument;
	}
	
	@Override
	public Document getOwnerDocument() {
		return nodeDocument;
	}
	
	@Override
	public Node getParentNode() {
		return this.parentNode;
	}
	
	@Override
	public NodeList getChildNodes() {
		return EMPTY_NODE_LIST;
	}
	
	@Override
	public Node getPreviousSibling() {
		if (parentNode == null) {
			return null;
		}
		Node previousNode = null;
		for (Node child: parentNode.getChildNodes()) {
			if (child == this) {
				return previousNode;
			}
			previousNode = child;
		}
		return null;
	}
	
	@Override
	public Node getLastChild() {
		NodeList children = getChildNodes();
		if (children.getLength() == 0) {
			return null;
		} else {
			return children.get(children.getLength() - 1);
		}
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
	
	protected void setParent(Node parentNode) {
		this.parentNode = parentNode;
	}
	
}
