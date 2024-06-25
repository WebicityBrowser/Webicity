package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter;

import java.util.Collection;
import java.util.List;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class ChildFilter<T, U> implements CSSOMFilter<T, U> {

	@Override
	public List<T> filter(Collection<CSSOMNode<T, U>> prematched, T item, CSSOMParticipantTraverser<T, U> traverser) {
		// CSSOM processes selectors in reverse order, so this node
		// is the child and we need to check if the parent matched
		T parent = traverser.getParent(item);
		return parent == null ? List.of() : List.of(parent);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof ChildFilter;
	}

	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}

}
