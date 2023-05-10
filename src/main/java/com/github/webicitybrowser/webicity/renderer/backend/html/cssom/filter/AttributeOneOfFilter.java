package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter;

import java.util.List;
import java.util.Set;

import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class AttributeOneOfFilter<U> implements CSSOMFilter<Node, U> {

	private final AttributeSelector attributeSelector;

	public AttributeOneOfFilter(AttributeSelector attributeSelector) {
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
			String target = attributeSelector.getComparisonValue();
			return attrContainsTarget(attrValue, target) ? List.of(item) : List.of();
		}
		return List.of();
	}

	private boolean attrContainsTarget(String attrValue, String target) {
		for (String value: attrValue.split(" ")) {
			if (value.equals(target)) {
				return true;
			}
		}
		
		return false;
	}

}
