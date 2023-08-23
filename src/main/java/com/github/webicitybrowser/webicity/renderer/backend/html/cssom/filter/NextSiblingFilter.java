package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter;

import java.util.List;
import java.util.Set;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class NextSiblingFilter<T, U> implements CSSOMFilter<T, U> {

	@Override
	public List<T> filter(Set<CSSOMNode<T, U>> prematched, T item, CSSOMParticipantTraverser<T> traverser) {
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
	
}
