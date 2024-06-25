package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.attroneof;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
				.computeIfAbsent(oneOfFilter.getAttributeName(), _1 -> new HashMap<>())
				.computeIfAbsent(oneOfFilter.getComparisonValue(), _1 -> new HashSet<>())
				.add(filterEntry);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<CSSOMFilterEntry<T, U>> getPossibleFilters(T node) {
		if (nodeGetter.apply(node) instanceof Element element) {
			ArrayList<CSSOMFilterEntry<T, U>> possibleFilters = new ArrayList<>();
			for (String attributeName: element.getAttributeNames()) {
				Map<String, Set<CSSOMFilterEntry<T, U>>> attrEntry = filters.get(attributeName);
				if (attrEntry == null) continue;

				String attributeValue = element.getAttribute(attributeName);
				for (String value: attributeValue.split(" ")) {
					Set<CSSOMFilterEntry<T, U> >filterEntry = attrEntry.get(value);
					if (filterEntry == null) continue;
					possibleFilters.addAll(filterEntry);
				}
			}
			
			return possibleFilters;
		} else {
			return List.of();
		}
	}

}
