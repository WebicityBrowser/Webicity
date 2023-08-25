package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.attroneof;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterComposition;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterEntry;

public class AttributeOneOfFilterComposition<T, U> implements CSSOMFilterComposition<T, U, AttributeOneOfFilter<T, U>> {

	private final Map<String, Map<String, Set<CSSOMFilterEntry<T, U>>>> filters = new HashMap<>();
	private final Function<T, Node> nodeGetter;

	public AttributeOneOfFilterComposition(Function<T, Node> nodeGetter) {
		this.nodeGetter = nodeGetter;
	}
	
	@Override
	public boolean addFilter(CSSOMFilterEntry<T, U> filterEntry) {
		CSSOMFilter<T, U> filter = filterEntry.filter();
		if (filter instanceof AttributeOneOfFilter<T, U> oneOfFilter) {
			return filters
				.computeIfAbsent(oneOfFilter.getAttributeName(), _1 -> new HashMap<>(1))
				.computeIfAbsent(oneOfFilter.getComparisonValue(), _1 -> new HashSet<>(1))
				.add(filterEntry);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<CSSOMFilterEntry<T, U>> getPossibleFilters(T node) {
		if (nodeGetter.apply(node) instanceof Element element) {
			ArrayList<CSSOMFilterEntry<T, U>> possibleFilters = new ArrayList<>();
			for (Entry<String, Map<String, Set<CSSOMFilterEntry<T, U>>>> attrEntry: filters.entrySet()) {
				String attributeName = attrEntry.getKey();
				String attributeValue = element.getAttribute(attributeName);
				if (attributeValue == null) return List.of();

				for (Entry<String, Set<CSSOMFilterEntry<T, U>>> valueEntry: attrEntry.getValue().entrySet()) {
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
