package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.List;

import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.spec.css.selectors.SelectorSpecificity;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp.CSSOMNodeImp;

public interface CSSOMNode<T, U> {

	CSSOMNode<T, U> getParent();

	CSSOMNode<T, U> createChild(ComplexSelectorPart selectorPart, int staging);
	
	void addNodeProperties(U properties);
	
	List<U> getNodeProperties();

	ComplexSelectorPart getSelectorPart();
	
	boolean isPopulated();
	
	List<CSSOMNode<T, U>> getChildren();
	
	void setSpecificity(SelectorSpecificity specificity);
	
	// TODO: Since some CSSOMNodes are merged but have different positions in the DOM tree,
	// specificity should be moved to individual node properties.
	SelectorSpecificity getSpecificity();

	static <T, U> CSSOMNode<T, U> create(CSSOMNode<T, U> parent, ComplexSelectorPart selectorPart) {
		return new CSSOMNodeImp<>(parent, selectorPart);
	}
	
}
