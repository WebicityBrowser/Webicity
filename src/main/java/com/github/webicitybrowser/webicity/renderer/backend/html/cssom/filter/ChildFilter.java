package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter;

import java.util.List;
import java.util.Set;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class ChildFilter<T, U> implements CSSOMFilter<T, U> {

	@Override
	public List<T> filter(Set<CSSOMNode<T, U>> prematched, T item, CSSOMParticipantTraverser<T> traverser) {
		return List.of(traverser.getChildren(item));
	}

}
