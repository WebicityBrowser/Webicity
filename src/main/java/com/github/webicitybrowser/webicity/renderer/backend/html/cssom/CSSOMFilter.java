package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.Collection;
import java.util.List;

public interface CSSOMFilter<T, U> {
	
	List<T> filter(Collection<CSSOMNode<T, U>> prematched, T item, CSSOMParticipantTraverser<T, U> traverser);
	
}
