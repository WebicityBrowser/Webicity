package everyos.browser.spec.jcss.cssom.selector.combinator;

import everyos.browser.spec.javadom.intf.Node;

public class ChildCombinator implements Combinator {

	@Override
	public Node[] matches(Node node) {
		Node parent = node.getParentNode();
		
		if (parent == null) {
			return new Node[0];
		}
		
		return new Node[] { parent };
	}
	
}
