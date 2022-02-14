package everyos.browser.spec.jcss.cssom.selector.simple;

import java.util.Objects;

import everyos.browser.spec.javadom.intf.Element;
import everyos.browser.spec.javadom.intf.Node;

public class TypeSelector implements SimpleSelectorFastRoute {

	private final String type;
	private int hashCode;

	public TypeSelector(String type) {
		this.type = type;
		this.hashCode = Objects.hash(type);
	}
	
	@Override
	public Node[] matches(Node node) {
		if (!(node instanceof Element && ((Element) node).getTagName().equals(type))) {
			return new Node[0];
		}
		
		return new Node[] { node };
	}
	
	@Override
	public boolean equals(Object o) {
		return
			o instanceof TypeSelector &&
			((TypeSelector) o).type.equals(this.type);
	}
	
	@Override
	public int hashCode() {
		return this.hashCode;
	}
	
	@Override
	public String toString() {
		return "TypeSelector [type=\""+type+"\"]";
	}
	
}
