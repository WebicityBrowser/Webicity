package com.github.webicitybrowser.spec.dom.node.imp;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.imp.util.InsertUtil;
import com.github.webicitybrowser.spec.dom.node.imp.util.StringifyUtil;
import com.github.webicitybrowser.spec.dom.node.support.NodeList;
import com.github.webicitybrowser.spec.dom.node.support.imp.NodeListImp;

public class DocumentImp extends NodeImp implements Document {

	private final List<Node> childNodes = new ArrayList<>(2);
	
	public DocumentImp() {
		super(null);
	}
	
	@Override
	public NodeList getChildNodes() {
		return new NodeListImp(childNodes);
	}
	
	@Override
	public void insertBefore(Node node, Node child) {
		// TODO: Run prechecks
		InsertUtil.insertBefore(childNodes, node, child);
	}

	@Override
	public void appendChild(Node node) {
		// TODO: Run prechecks
		childNodes.add(node);
	}
	
	@Override
	public String toString() {
		return StringifyUtil.serializeChildren(childNodes, "");
	}
	
}
