package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class DescendantFilter<T, U> implements CSSOMFilter<T, U> {

	@Override
	public List<T> filter(Set<CSSOMNode<T, U>> prematched, T item, CSSOMParticipantTraverser<T> traverser) {
		// CSSOM processes selectors in reverse order, so this node
		// is the child and we need to check if any parents matched
		List<T> parents = new ArrayList<>();
		T current = item;
		while ((current = traverser.getParent(current)) != null) {
			parents.add(current);
		}

		return parents;
	}

}