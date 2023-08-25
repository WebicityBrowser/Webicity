package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.id;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMComposableFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterComposition;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class IDFilter<T, U> implements CSSOMComposableFilter<T, U, IDFilter<T, U>> {

	private final String idName;
	private final Function<T, Node> nodeGetter;

	public IDFilter(String idName, Function<T, Node> nodeGetter) {
		this.idName = idName;
		this.nodeGetter = nodeGetter;
	}
	
	@Override
	public List<T> filter(Collection<CSSOMNode<T, U>> prematched, T item, CSSOMParticipantTraverser<T, U> traverser) {
		return isApplicable(item) ?
			List.of(item) :
			List.of();
	}

	@Override
	public int hashCode() {
		return Objects.hash(idName);
	}
	
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
	public CSSOMFilterComposition<T, U, IDFilter<T, U>> createFilterComposition() {
		return new IDFilterComposition<>(nodeGetter);
	}
	
	public String getID() {
		return this.idName;
	}
	
	private boolean isApplicable(T participant) {
		if (nodeGetter.apply(participant) instanceof Element element) {
			return
				element.hasAttribute("id") &&
				element.getAttribute("id").equals(idName);
		} else {
			return false;
		}
	}
	
}
