package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.List;

public interface CSSOMFilterComposition<T, U, V extends CSSOMComposableFilter<T, U, V>> {

	boolean addFilter(CSSOMFilterEntry<T, U> filterEntry);
	
	List<CSSOMFilterEntry<T, U>> getPossibleFilters(T node);
	
}
