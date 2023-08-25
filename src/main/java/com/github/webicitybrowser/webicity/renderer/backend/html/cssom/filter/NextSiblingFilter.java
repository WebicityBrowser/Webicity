package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter;

import java.util.Collection;
import java.util.List;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class NextSiblingFilter<T, U> implements CSSOMFilter<T, U> {

	@Override
	public List<T> filter(Collection<CSSOMNode<T, U>> prematched, T item, CSSOMParticipantTraverser<T, U> traverser) {
		// CSSOM processes selectors in reverse order, so we actually
		// need to check if the previous sibling matched

		T parent = traverser.getParent(item);
		if (parent == null) {
			return List.of();
		}
		
		T[] children = traverser.getChildren(parent);
		T previous = null;
		for (T child: children) {
			if (child.equals(item)) {
				return previous == null ? List.of() : List.of(previous);
			}
			previous = child;
		}
		
		return List.of();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof NextSiblingFilter;
	}

	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}
	
}
