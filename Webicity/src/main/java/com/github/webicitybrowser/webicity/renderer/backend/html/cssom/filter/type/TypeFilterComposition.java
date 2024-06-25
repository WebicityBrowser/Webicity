package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.type;

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

public class TypeFilterComposition<T, U> implements CSSOMFilterComposition<T, U, TypeFilter<T, U>> {
	
	private final Map<String, Set<CSSOMFilterEntry<T, U>>> filters = new HashMap<>();
	private final Function<T, Node> nodeGetter;

	public TypeFilterComposition(Function<T, Node> nodeGetter) {
		this.nodeGetter = nodeGetter;
	}

	@Override
	public boolean addFilter(CSSOMFilterEntry<T, U> filterEntry) {
		CSSOMFilter<T, U> filter = filterEntry.filter();
		if (filter instanceof TypeFilter<T, U> typeFilter) {
			return filters
				.computeIfAbsent(typeFilter.getElementName(), _1 -> new HashSet<>(1))
				.add(filterEntry);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<CSSOMFilterEntry<T, U>> getPossibleFilters(T node) {
		if (nodeGetter.apply(node) instanceof Element element) {
			return List.copyOf(filters.getOrDefault(element.getLocalName(), Set.of()));
		} else {
			return List.of();
		}
	}

}
