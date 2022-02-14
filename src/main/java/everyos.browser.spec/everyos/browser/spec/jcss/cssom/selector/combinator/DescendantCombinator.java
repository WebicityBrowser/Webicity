package everyos.browser.spec.jcss.cssom.selector.combinator;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.spec.javadom.intf.Node;

public class DescendantCombinator implements Combinator {
	
	@Override
	public Node[] matches(Node node) {
		List<Node> parents = new ArrayList<>();
		
		Node current = node.getParentNode();
		while (current != null) {
			parents.add(current);
			current = current.getParentNode();
		}
		
		return parents.toArray(new Node[parents.size()]);
	}
	
}
