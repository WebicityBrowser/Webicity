package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter;

import java.util.List;
import java.util.Set;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class ChildFilter<T, U> implements CSSOMFilter<T, U> {

	@Override
	public List<T> filter(Set<CSSOMNode<T, U>> prematched, T item, CSSOMParticipantTraverser<T> traverser) {
		// CSSOM processes selectors in reverse order, so this node
		// is the child and we need to check if the parent matched
		T parent = traverser.getParent(item);
		return parent == null ? List.of() : List.of(parent);
	}

}
