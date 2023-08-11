package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.type;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMComposableFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterComposition;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class TypeFilter<U> implements CSSOMComposableFilter<Node, U, TypeFilter<U>> {

	private final String namespace;
	private final String elementName;

	public TypeFilter(String namespace, String elementName) {
		this.namespace = namespace;
		this.elementName = elementName;
	}
	
	@Override
	public List<Node> filter(Set<CSSOMNode<Node, U>> prematched, Node item, CSSOMParticipantTraverser<Node> traverser) {
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
	public CSSOMFilterComposition<Node, U, TypeFilter<U>> createFilterComposition() {
		return new TypeFilterComposition<>();
	}
	
	public String getElementName() {
		return this.elementName;
	}
	
	private boolean isApplicable(Node node) {
		if (node instanceof Element element) {
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
