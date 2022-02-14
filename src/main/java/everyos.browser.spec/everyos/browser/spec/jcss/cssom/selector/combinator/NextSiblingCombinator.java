package everyos.browser.spec.jcss.cssom.selector.combinator;

import everyos.browser.spec.javadom.intf.Node;
import everyos.browser.spec.javadom.intf.NodeList;
import everyos.browser.spec.jcss.cssom.selector.ComplexSelectorPart;

public class NextSiblingCombinator implements ComplexSelectorPart {

	@Override
	public Node[] matches(Node node) {
		Node parent = node.getParentNode();
		if (parent == null) {
			return new Node[0];
		}
		
		NodeList childNodes = parent.getChildNodes();
		
		for (int i = 0; i < childNodes.getLength() - 1; i++) {
			if (childNodes.item(i) == node) {
				return new Node[] { childNodes.item(i + 1) };
			}
		}
		
		return new Node[0];
	}

}
