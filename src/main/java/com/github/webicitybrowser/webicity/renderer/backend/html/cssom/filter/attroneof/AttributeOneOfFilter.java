package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.attroneof;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMComposableFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterComposition;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class AttributeOneOfFilter<T, U> implements CSSOMComposableFilter<T, U, AttributeOneOfFilter<T, U>> {

	private final AttributeSelector attributeSelector;
	private final Function<T, Node> nodeGetter;

	public AttributeOneOfFilter(AttributeSelector attributeSelector, Function<T, Node> nodeGetter) {
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
			
			String target = attributeSelector.getComparisonValue();
			return attrContainsTarget(attrValue, target) ? List.of(item) : List.of();
		}

		return List.of();
	}
	
	@Override
	public Class<?> getFilterType() {
		return AttributeOneOfFilter.class;
	}

	@Override
	public CSSOMFilterComposition<T, U, AttributeOneOfFilter<T, U>> createFilterComposition() {
		return new AttributeOneOfFilterComposition<T, U>(nodeGetter);
	}

	@Override
	public boolean equals(Object obj) {
		return
			obj instanceof AttributeOneOfFilter attributeOneOfFilter &&
			attributeOneOfFilter.attributeSelector.equals(attributeSelector) &&
			attributeOneOfFilter.nodeGetter.equals(nodeGetter);
	}

	@Override
	public int hashCode() {
		return Objects.hash(attributeSelector, nodeGetter);
	}
	
	public String getAttributeName() {
		return attributeSelector.getAttributeName().getName();
	}

	public String getComparisonValue() {
		return attributeSelector.getComparisonValue();
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
