package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.filters;

import java.util.Objects;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.CSSOMFilter;

public class TypeFilter implements CSSOMFilter {

	private final String namespace;
	private final String elementName;

	public TypeFilter(String namespace, String elementName) {
		this.namespace = namespace;
		this.elementName = elementName;
	}
	
	@Override
	public boolean isApplicable(WebComponent component, int index) {
		Node node = component.getNode();
		if (node instanceof Element element) {
			return
				element.getLocalName().equals(elementName) &&
				namespaceMatches(element.getNamespace());
		} else {
			return false;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		return
			o instanceof TypeFilter filter &&
			filter.elementName.equals(elementName) &&
			Objects.equals(namespace, filter.namespace);
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
