package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter;

import java.util.List;
import java.util.Set;

import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class AttributeFilter<U> implements CSSOMFilter<Node, U> {

	private final AttributeSelector attributeSelector;

	public AttributeFilter(AttributeSelector attributeSelector) {
		this.attributeSelector = attributeSelector;
	}

	@Override
	public List<Node> filter(Set<CSSOMNode<Node, U>> prematched, Node item, CSSOMParticipantTraverser<Node> traverser) {
		// TODO Check attribute namespace
		if (item instanceof Element element) {
			String attrValue = element.getAttribute(attributeSelector.getAttributeName().getName());
			if (attrValue == null) {
				return List.of();
			}

			return checkOperation(attrValue, attributeSelector.getComparisonValue()) ?
				List.of(item) :
				List.of();
		}

		return List.of();
	}

	private boolean checkOperation(String attrValue, String comparisonValue) {
		switch (attributeSelector.getOperation()) {
			case BEGINS_WITH:
				return attrValue.startsWith(comparisonValue);
			case CONTAINS:
				return attrValue.contains(comparisonValue);
			case ENDS_WITH:
				return attrValue.endsWith(comparisonValue);
			case EQUALS:
				return attrValue.equals(comparisonValue);
			case HAS_SUBCODE:
				return attrValue.equals(comparisonValue) ||  attrValue.startsWith(comparisonValue + "-");
			case ONE_OF:
				throw new RuntimeException("This should be handled by AttributeOneOfFilter");
			case PRESENT:
				return true;
			default:
				throw new RuntimeException("Unknown operation: " + attributeSelector.getOperation());
		}
	}

}
