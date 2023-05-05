package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.List;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp.CSSOMNodeImp;

public interface CSSOMNode<T, U> {

	CSSOMNode<T, U> createChild(CSSOMFilter<T, U> filter, int staging);
	
	void linkChild(CSSOMFilter<T, U> filter, int staging, CSSOMNode<T, U> linkedNode);
	
	void addNodeProperties(U properties);
	
	List<U> getNodeProperties();
	
	List<CSSOMFilterEntry<T, U>> getFilters();

	static <T, U> CSSOMNode<T, U> create() {
		return new CSSOMNodeImp<>();
	}
	
}
