package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipant;

public class TypeFilter<U> implements CSSOMFilter<Node, U> {

	private final String namespace;
	private final String elementName;

	public TypeFilter(String namespace, String elementName) {
		this.namespace = namespace;
		this.elementName = elementName;
	}
	
	@Override
	public List<CSSOMParticipant<Node>> filter(List<CSSOMNode<Node, U>> prematched, CSSOMParticipant<Node> item) {
		List<CSSOMParticipant<Node>> matched = new ArrayList<>(4);
		for (CSSOMParticipant<Node> child: item.getChildren()) {
			if (isApplicable(child.getValue())) {
				matched.add(child);
			}
		}
		
		return matched;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(namespace, elementName);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object o) {
		return
			o instanceof TypeFilter filter &&
			filter.elementName.equals(elementName) &&
			Objects.equals(namespace, filter.namespace);
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
