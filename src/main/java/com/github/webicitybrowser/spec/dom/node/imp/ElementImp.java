package com.github.webicitybrowser.spec.dom.node.imp;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.imp.util.InsertUtil;
import com.github.webicitybrowser.spec.dom.node.imp.util.StringifyUtil;
import com.github.webicitybrowser.spec.dom.node.support.NodeList;
import com.github.webicitybrowser.spec.dom.node.support.imp.NodeListImp;

public class ElementImp extends NodeImp implements Element {
	
	private final String localTag;
	private final List<Node> childNodes = new ArrayList<>(1);

	public ElementImp(String localTag) {
		this.localTag = localTag;
	}

	@Override
	public String getLocalTag() {
		return this.localTag;
	};
	
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
	
	// toString is useful for debugging. Not guaranteed to produce valid or correct HTML
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<");
		stringBuilder.append(localTag);
		stringBuilder.append(">\n");
		stringBuilder.append(StringifyUtil.serializeChildren(childNodes, "\t"));
		stringBuilder.append("\n</");
		stringBuilder.append(localTag);
		stringBuilder.append(">");
		return stringBuilder.toString();
	}
	
}
