package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.type;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMComposableFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterComposition;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class TypeFilter<T, U> implements CSSOMComposableFilter<T, U, TypeFilter<T, U>> {

	private final String namespace;
	private final String elementName;
	private final Function<T, Node> nodeGetter;

	public TypeFilter(String namespace, String elementName, Function<T, Node> nodeGetter) {
		this.namespace = namespace;
		this.elementName = elementName;
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
		return Objects.hash(namespace, elementName);
	}
	
	@Override
	public boolean equals(Object o) {
		return
			o instanceof TypeFilter filter &&
			filter.elementName.equals(elementName) &&
			Objects.equals(namespace, filter.namespace);
	}
	
	@Override
	public Class<?> getFilterType() {
		return TypeFilter.class;
	}

	@Override
	public CSSOMFilterComposition<T, U, TypeFilter<T, U>> createFilterComposition() {
		return new TypeFilterComposition<>(nodeGetter);
	}
	
	public String getElementName() {
		return this.elementName;
	}
	
	private boolean isApplicable(T participant) {
		if (nodeGetter.apply(participant) instanceof Element element) {
			return
				element.getLocalName().equals(elementName) &&
				namespaceMatches(element.getNamespace());
		} else {
			return false;
		}
	}

	private boolean namespaceMatches(String elementNamespace) {
		if (namespace == elementNamespace) {
			return true;
		}
		if (namespace.equals("*")) {
			return true;
		}
		if (namespace.equals("**")) {
			return elementNamespace.equals(Namespace.HTML_NAMESPACE);
		}
		return namespace.equals(elementNamespace);
	}
	
}
