package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.id;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterComposition;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterEntry;

public class IDFilterComposition<U> implements CSSOMFilterComposition<Node, U, IDFilter<U>> {
	
	private final Map<String, Set<CSSOMFilterEntry<Node, U>>> filters = new HashMap<>();

	@Override
	public boolean addFilter(CSSOMFilterEntry<Node, U> filterEntry) {
		CSSOMFilter<Node, U> filter = filterEntry.filter();
		if (filter instanceof IDFilter<U> idFilter) {
			return filters
				.computeIfAbsent(idFilter.getID(), _1 -> new HashSet<>(1))
				.add(filterEntry);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<CSSOMFilterEntry<Node, U>> getPossibleFilters(Node node) {
		if (node instanceof Element element && element.hasAttribute("id")) {
			return List.copyOf(filters.getOrDefault(element.getAttribute("id"), Set.of()));
		} else {
			return List.of();
		}
	}

}
