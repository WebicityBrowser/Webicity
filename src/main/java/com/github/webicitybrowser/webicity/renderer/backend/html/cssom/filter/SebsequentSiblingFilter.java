package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class SebsequentSiblingFilter<T, U> implements CSSOMFilter<T, U> {

	@Override
	public List<T> filter(Set<CSSOMNode<T, U>> prematched, T item, CSSOMParticipantTraverser<T> traverser) {
		// CSSOM processes selectors in reverse order, so we actually
		// need to check if the previous siblings matched

		T parent = traverser.getParent(item);
		if (parent == null) {
			return List.of();
		}
		
		T[] children = traverser.getChildren(parent);
		List<T> result = new ArrayList<>();
		for (T child: children) {
			if (child.equals(item)) {
				return result;
			}
			result.add(child);
		}
		
		return result;
	}
	
}
