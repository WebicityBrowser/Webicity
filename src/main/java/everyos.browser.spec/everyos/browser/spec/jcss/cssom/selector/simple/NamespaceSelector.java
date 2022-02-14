package everyos.browser.spec.jcss.cssom.selector.simple;

import everyos.browser.spec.javadom.intf.Element;
import everyos.browser.spec.javadom.intf.Node;

public class NamespaceSelector implements SimpleSelector {

	private String namespace;

	public NamespaceSelector(String namespace) {
		this.namespace = namespace;
	}
	
	@Override
	public Node[] matches(Node node) {
		if (!(node instanceof Element)) {
			return new Node[0];
		}
		if (((Element) node).getNamespaceURI().equals(namespace)) {
			return new Node[] { node };
		}
		return new Node[0];
	}
	
}
