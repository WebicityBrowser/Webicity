package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.spec.css.selectors.SelectorSpecificity;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;

public class CSSOMNodeImp<T, U> implements CSSOMNode<T, U> {

	private final CSSOMNode<T, U> parent;
	private final ComplexSelectorPart selectorPart;
	private final HashMap<ComplexSelectorPart, CSSOMNode<T, U>> children = new HashMap<>(4);
	private final List<U> allProperties = new ArrayList<>(1);
	
	private SelectorSpecificity specificity;

	public CSSOMNodeImp(CSSOMNode<T, U> parent, ComplexSelectorPart selectorPart) {
		this.parent = parent;
		this.selectorPart = selectorPart;
	}

	@Override
	public CSSOMNode<T, U> getParent() {
		return parent;
	}

	@Override
	public CSSOMNode<T, U> createChild(ComplexSelectorPart selectorPart, int staging) {
		return children.computeIfAbsent(selectorPart, key -> new CSSOMNodeImp<>(this, selectorPart));
	}

	@Override
	public void addNodeProperties(U properties) {
		allProperties.add(properties);
	}

	@Override
	public List<U> getNodeProperties() {
		return allProperties;
	}
	
	@Override
	public ComplexSelectorPart getSelectorPart() {
		return selectorPart;
	}

	@Override
	public boolean isPopulated() {
		return !allProperties.isEmpty();
	}

	@Override
	public List<CSSOMNode<T, U>> getChildren() {
		return new ArrayList<>(children.values());
	}

	@Override
	public void setSpecificity(SelectorSpecificity specificity) {
		this.specificity = specificity;
	}

	@Override
	public SelectorSpecificity getSpecificity() {
		return this.specificity;
	}

}
