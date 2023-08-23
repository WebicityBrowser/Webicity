package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.List;
import java.util.Set;

public interface CSSOMFilter<T, U> {
	
	List<T> filter(Set<CSSOMNode<T, U>> prematched, T item, CSSOMParticipantTraverser<T> traverser);

	default void setupMatchingNode(CSSOMNode<T, U> node, int staging) {}
	
}
