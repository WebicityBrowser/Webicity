package everyos.browser.webicity.renderer.html.dom.impl;

import java.util.ArrayList;

import everyos.browser.webicity.renderer.html.dom.Node;
import everyos.browser.webicity.renderer.html.dom.NodeList;

public class NodeListImpl implements NodeList {
	private ArrayList<Node> nodes;

	public NodeListImpl(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	@Override public Node item(long index) {
		return nodes.get((int) index);
	}

	@Override public long getLength() {
		return nodes.size();
	}
}
