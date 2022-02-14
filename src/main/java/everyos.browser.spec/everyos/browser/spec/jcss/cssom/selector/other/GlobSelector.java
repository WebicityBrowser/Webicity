package everyos.browser.spec.jcss.cssom.selector.other;

import everyos.browser.spec.javadom.intf.Node;
import everyos.browser.spec.jcss.cssom.selector.ComplexSelectorPart;

public class GlobSelector implements ComplexSelectorPart {

	@Override
	public Node[] matches(Node node) {
		return new Node[] { node };
	}
	
}
