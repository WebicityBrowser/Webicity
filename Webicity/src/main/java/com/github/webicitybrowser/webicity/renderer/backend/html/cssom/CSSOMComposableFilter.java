package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

public interface CSSOMComposableFilter<T, U, V extends CSSOMComposableFilter<T, U, V>> extends CSSOMFilter<T, U> {

	Class<?> getFilterType();
	
	CSSOMFilterComposition<T, U, V> createFilterComposition(); 
	
}
