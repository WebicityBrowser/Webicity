package everyos.browser.javadom.imp;

import java.util.Iterator;
import java.util.List;

import everyos.browser.javadom.intf.Node;
import everyos.browser.javadom.intf.NodeList;

public class JDNodeList implements NodeList {
	private List<Node> children;

	public JDNodeList(List<Node> children) {
		this.children = children;
	}

	@Override
	public long getLength() {
		return children.size();
	}

	@Override
	public Node item(long index) {
		return children.get((int) index);
	}

	@Override
	public Iterator<Node> iterator() {
		return children.iterator();
	}
}
