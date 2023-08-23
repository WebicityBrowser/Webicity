package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter;

import java.util.List;
import java.util.Set;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class NextSiblingFilter<T, U> implements CSSOMFilter<T, U> {

	@Override
	public List<T> filter(Set<CSSOMNode<T, U>> prematched, T item, CSSOMParticipantTraverser<T> traverser) {
		T parent = traverser.getParent(item);
		if (parent == null) {
			return List.of();
		}
		
		T[] children = traverser.getChildren(parent);
		boolean found = false;
		for (T child: children) {
			if (found) {
				return List.of(child);
			} else if (child.equals(item)) {
				found = true;
			}
		}
		
		return List.of();
	}
	
}
