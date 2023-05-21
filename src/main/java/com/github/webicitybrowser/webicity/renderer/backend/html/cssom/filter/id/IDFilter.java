package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.id;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMComposableFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterComposition;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class IDFilter<U> implements CSSOMComposableFilter<Node, U, IDFilter<U>> {

	private final String idName;

	public IDFilter(String idName) {
		this.idName = idName;
	}
	
	@Override
	public List<Node> filter(Set<CSSOMNode<Node, U>> prematched, Node item, CSSOMParticipantTraverser<Node> traverser) {
		return isApplicable(item) ?
			List.of(item) :
			List.of();
	}

	@Override
	public int hashCode() {
		return Objects.hash(idName);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object o) {
		return
			o instanceof IDFilter filter &&
			filter.idName.equals(idName);
	}
	
	@Override
	public Class<?> getFilterType() {
		return IDFilter.class;
	}

	@Override
	public CSSOMFilterComposition<Node, U, IDFilter<U>> createFilterComposition() {
		return new IDFilterComposition<>();
	}
	
	public String getID() {
		return this.idName;
	}
	
	private boolean isApplicable(Node node) {
		if (node instanceof Element element) {
			return
				element.hasAttribute("id") &&
				element.getAttribute("id").equals(idName);
		} else {
			return false;
		}
	}
	
}
