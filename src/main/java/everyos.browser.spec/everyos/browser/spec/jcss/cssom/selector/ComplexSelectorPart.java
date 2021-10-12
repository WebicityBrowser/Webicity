package everyos.browser.spec.jcss.cssom.selector;

import everyos.browser.spec.javadom.intf.Node;

public interface ComplexSelectorPart {
	public Node[] matches(Node node);
}
