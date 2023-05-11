package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.attroneof;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterComposition;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterEntry;

public class AttributeOneOfFilterComposition<U> implements CSSOMFilterComposition<Node, U, AttributeOneOfFilter<U>> {

	private final Map<String, Map<String, List<CSSOMFilterEntry<Node, U>>>> filters = new HashMap<>();
	
	@Override
	public void addFilter(CSSOMFilterEntry<Node, U> filterEntry) {
		CSSOMFilter<Node, U> filter = filterEntry.filter();
		if (filter instanceof AttributeOneOfFilter<U> oneOfFilter) {
			filters
				.computeIfAbsent(oneOfFilter.getAttributeName(), _1 -> new HashMap<>(1))
				.computeIfAbsent(oneOfFilter.getComparisonValue(), _1 -> new ArrayList<>(1))
				.add(filterEntry);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<CSSOMFilterEntry<Node, U>> getPossibleFilters(Node node) {
		if (node instanceof Element element) {
			ArrayList<CSSOMFilterEntry<Node, U>> possibleFilters = new ArrayList<>();
			for (Entry<String, Map<String, List<CSSOMFilterEntry<Node, U>>>> attrEntry: filters.entrySet()) {
				String attributeValue = attrEntry.getKey();
				if (attributeValue == null) {
					return List.of();
				}
				for (Entry<String, List<CSSOMFilterEntry<Node, U>>> valueEntry: attrEntry.getValue().entrySet()) {
					if (attributeValue.indexOf(valueEntry.getKey()) != -1) {
						possibleFilters.addAll(valueEntry.getValue());
					}
				}
			}
			
			return possibleFilters;
		} else {
			return List.of();
		}
	}

}
