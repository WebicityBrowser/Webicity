package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMComposableFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterComposition;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterEntry;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;

public class CSSOMTips<T, U> {
	
	private final Set<CSSOMFilterEntry<T, U>> filters = new HashSet<>();
	private final Map<Class<?>, CSSOMFilterComposition<T, U, ?>> composableFilters = new HashMap<>();

	public void addTip(CSSOMNode<T, U> tip) {
		CSSOMFilterEntry<T, U> filterEntry = new CSSOMFilterEntry<>(0, tip.getFilter(), tip);
		if (tip.getFilter() instanceof CSSOMComposableFilter<T, U, ?> composableFilter) {
			composableFilters
				.computeIfAbsent(composableFilter.getFilterType(), _1 -> composableFilter.createFilterComposition())
				.addFilter(filterEntry);
		} else {
			filters.add(filterEntry);
		}
	}

	public Set<CSSOMNode<T, U>> getPossibleCSSOMNodes(T node, TraverseContext<T, U> context) {
		Set<CSSOMNode<T, U>> possibleCSSOMNodes = new HashSet<>();

		for (CSSOMFilterEntry<T, U> filterEntry : filters) {
			addNodeIfMatches(node, context, possibleCSSOMNodes, filterEntry);
		}

		for (CSSOMFilterComposition<T, U, ?> filterComposition : composableFilters.values()) {
			List<CSSOMFilterEntry<T, U>> likelyFilters = filterComposition.getPossibleFilters(node);
			for (CSSOMFilterEntry<T, U> filterEntry : likelyFilters) {
				addNodeIfMatches(node, context, possibleCSSOMNodes, filterEntry);
			}	
		}
		return possibleCSSOMNodes;
	}

	private void addNodeIfMatches(
		T node, TraverseContext<T, U> context, Set<CSSOMNode<T, U>> possibleCSSOMNodes, CSSOMFilterEntry<T, U> filterEntry
	) {
		List<T> matches = filterEntry.filter().filter(
			context.matchingNodes(node).getMatchingNodes(),
			node,
			context.traverser());
		if (matches.contains(node)) {
			// We know this node matches, so the parent (a combinator or selector) is
			// the next node to check
			possibleCSSOMNodes.add(filterEntry.cssomNode());
		}
	}

}
