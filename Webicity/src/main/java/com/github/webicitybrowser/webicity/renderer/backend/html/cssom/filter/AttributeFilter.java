package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class AttributeFilter<T, U> implements CSSOMFilter<T, U> {

	private final AttributeSelector attributeSelector;
	private final Function<T, Node> nodeGetter;

	public AttributeFilter(AttributeSelector attributeSelector, Function<T, Node> nodeGetter) {
		this.attributeSelector = attributeSelector;
		this.nodeGetter = nodeGetter;
	}

	@Override
	public List<T> filter(Collection<CSSOMNode<T, U>> prematched, T item, CSSOMParticipantTraverser<T, U> traverser) {
		// TODO Check attribute namespace
		if (nodeGetter.apply(item) instanceof Element element) {
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
