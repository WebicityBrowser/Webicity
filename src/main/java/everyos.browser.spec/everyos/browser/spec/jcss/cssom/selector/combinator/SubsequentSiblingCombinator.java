package everyos.browser.spec.jcss.cssom.selector.combinator;

import everyos.browser.spec.javadom.intf.Node;
import everyos.browser.spec.javadom.intf.NodeList;
import everyos.browser.spec.jcss.cssom.selector.ComplexSelectorPart;

public class SubsequentSiblingCombinator implements ComplexSelectorPart {

	@Override
	public Node[] matches(Node node) {
		Node parent = node.getParentNode();
		if (parent == null) {
			return new Node[0];
		}
		
		NodeList childNodes = parent.getChildNodes();
		
		for (int i = 0; i < childNodes.getLength() - 1; i++) {
			if (childNodes.item(i) == node) {
				return getNodesAfterIndex(childNodes, i);
			}
		}
		
		return new Node[0];
	}

	private Node[] getNodesAfterIndex(NodeList nodeList, int i) {
		//TODO: NodeList uses longs, be arrays use ints
		Node[] nodes = new Node[(int) (nodeList.getLength() - i - 1)];
		
		for (int j = i + 1; j < nodeList.getLength(); j++) {
			nodes[j - i - 1] = nodeList.item(j);
		}
		
		return nodes;
	}

}
