package everyos.browser.spec.jcss.cssom.selector.simple;

import java.util.function.Function;

import everyos.browser.spec.javadom.intf.Element;
import everyos.browser.spec.javadom.intf.Node;

public class AttributeSelector implements SimpleSelector {

	private String attribute;
	private Function<String, Boolean> validator;

	public AttributeSelector(String attribute, Function<String, Boolean> validator) {
		this.attribute = attribute;
		this.validator = validator;
	}
	
	@Override
	public Node[] matches(Node node) {
		if (!(node instanceof Element)) {
			return new Node[0];
		}
		
		String attributeValue = ((Element) node).getAttribute(attribute);
		if (validator.apply(attributeValue)) {
			return new Node[] { node };
		}
		
		return new Node[0];
	}
	
}
