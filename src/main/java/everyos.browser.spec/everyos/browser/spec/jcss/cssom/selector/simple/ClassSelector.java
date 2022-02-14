package everyos.browser.spec.jcss.cssom.selector.simple;

import java.util.Objects;

import everyos.browser.spec.javadom.intf.Element;
import everyos.browser.spec.javadom.intf.Node;

public class ClassSelector implements SimpleSelectorFastRoute {

	private final String cls;

	public ClassSelector(String cls) {
		this.cls = cls;
	}

	@Override
	public Node[] matches(Node node) {
		if (!(node instanceof Element && ((Element) node).getClassList().contains(cls))) {
			return new Node[0];
		}
		
		return new Node[] { node };
	}
	
	@Override
	public boolean equals(Object o) {
		return
			o instanceof ClassSelector &&
			((ClassSelector) o).cls.equals(this.cls);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cls);
	}
	
}
