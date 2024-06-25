package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.webicitybrowser.spec.css.selectors.SelectorSpecificity;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;

public class CSSOMNodeImp<T, U> implements CSSOMNode<T, U> {

	private final CSSOMNode<T, U> parent;
	private final CSSOMFilter<T, U> filter;
	private final HashMap<CSSOMFilter<T, U>, CSSOMNode<T, U>> children = new HashMap<>(4);
	private final List<U> allProperties = new ArrayList<>(1);
	
	private SelectorSpecificity specificity;

	public CSSOMNodeImp(CSSOMNode<T, U> parent, CSSOMFilter<T, U> filter) {
		this.parent = parent;
		this.filter = filter;
	}

	@Override
	public CSSOMNode<T, U> getParent() {
		return parent;
	}

	@Override
	public CSSOMNode<T, U> createChild(CSSOMFilter<T, U> filter, int staging) {
		return children.computeIfAbsent(filter, key -> createChildNode(filter, staging));
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
	public CSSOMFilter<T, U> getFilter() {
		return filter;
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
	
	private CSSOMNode<T, U> createChildNode(CSSOMFilter<T, U> filter, int staging) {
		return new CSSOMNodeImp<>(this, filter);
	}

}
