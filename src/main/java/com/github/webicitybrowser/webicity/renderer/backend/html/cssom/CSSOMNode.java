package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.List;

import com.github.webicitybrowser.spec.css.selectors.SelectorSpecificity;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp.CSSOMNodeImp;

public interface CSSOMNode<T, U> {

	CSSOMNode<T, U> getParent();

	CSSOMNode<T, U> createChild(CSSOMFilter<T, U> filter, int staging);
	
	void addNodeProperties(U properties);
	
	List<U> getNodeProperties();

	CSSOMFilter<T, U> getFilter();
	
	boolean isPopulated();
	
	List<CSSOMNode<T, U>> getChildren();
	
	void setSpecificity(SelectorSpecificity specificity);
	
	// TODO: Since some CSSOMNodes are merged but have different positions in the DOM tree,
	// specificity should be moved to individual node properties.
	SelectorSpecificity getSpecificity();

	static <T, U> CSSOMNode<T, U> create(CSSOMNode<T, U> parent, CSSOMFilter<T, U> filter) {
		return new CSSOMNodeImp<>(parent, filter);
	}
	
}
