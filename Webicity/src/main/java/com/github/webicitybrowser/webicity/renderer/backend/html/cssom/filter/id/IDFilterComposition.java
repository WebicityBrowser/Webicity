package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.id;

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

public class IDFilterComposition<T, U> implements CSSOMFilterComposition<T, U, IDFilter<T, U>> {
	
	private final Map<String, Set<CSSOMFilterEntry<T, U>>> filters = new HashMap<>();
	private final Function<T, Node> nodeGetter;

	public IDFilterComposition(Function<T, Node> nodeGetter) {
		this.nodeGetter = nodeGetter;
	}

	@Override
	public boolean addFilter(CSSOMFilterEntry<T, U> filterEntry) {
		CSSOMFilter<T, U> filter = filterEntry.filter();
		if (filter instanceof IDFilter<T, U> idFilter) {
			return filters
				.computeIfAbsent(idFilter.getID(), _1 -> new HashSet<>(1))
				.add(filterEntry);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<CSSOMFilterEntry<T, U>> getPossibleFilters(T participant) {
		if (nodeGetter.apply(participant) instanceof Element element && element.hasAttribute("id")) {
			return List.copyOf(filters.getOrDefault(element.getAttribute("id"), Set.of()));
		} else {
			return List.of();
		}
	}

}
