package everyos.browser.spec.jcss.cssom.selector.simple;

import java.util.Objects;

import everyos.browser.spec.javadom.intf.Element;
import everyos.browser.spec.javadom.intf.Node;

public class IDSelector implements SimpleSelectorFastRoute {

	private final String id;
	private int hashCode;

	public IDSelector(String id) {
		this.id = id;
		this.hashCode = Objects.hash(id);
	}

	@Override
	public Node[] matches(Node node) {
		if (!(node instanceof Element && ((Element) node).getId().equals(id))) {
			return new Node[0];
		}
		
		return new Node[] { node };
	}
	
	@Override
	public boolean equals(Object o) {
		return
			o instanceof IDSelector &&
			((IDSelector) o).id.equals(this.id);
	}
	
	@Override
	public int hashCode() {
		return this.hashCode;
	}
}
