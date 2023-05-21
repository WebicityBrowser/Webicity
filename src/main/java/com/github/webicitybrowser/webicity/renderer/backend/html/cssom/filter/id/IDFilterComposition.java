package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterComposition;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterEntry;

public class IDFilterComposition<U> implements CSSOMFilterComposition<Node, U, IDFilter<U>> {
	
	private final Map<String, List<CSSOMFilterEntry<Node, U>>> filters = new HashMap<>();

	@Override
	public void addFilter(CSSOMFilterEntry<Node, U> filterEntry) {
		CSSOMFilter<Node, U> filter = filterEntry.filter();
		if (filter instanceof IDFilter<U> idFilter) {
			filters
				.computeIfAbsent(idFilter.getID(), _1 -> new ArrayList<>(1))
				.add(filterEntry);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<CSSOMFilterEntry<Node, U>> getPossibleFilters(Node node) {
		if (node instanceof Element element && element.hasAttribute("id")) {
			return filters.getOrDefault(element.getAttribute("id"), List.of());
		} else {
			return List.of();
		}
	}

}
