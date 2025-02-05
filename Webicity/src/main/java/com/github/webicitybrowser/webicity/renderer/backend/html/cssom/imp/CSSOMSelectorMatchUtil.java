package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import com.github.webicitybrowser.spec.css.QualifiedName;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector.AttributeSelectorOperation;
import com.github.webicitybrowser.spec.css.selectors.selector.IDSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.TypeSelector;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator.DocumentStyleGenerator;

public class CSSOMSelectorMatchUtil<T, U> {
	
	public Set<T> matchIndividualSelectorPart(Map<CSSOMNode<T, U>, Set<T>> matched, Set<T> current, CSSOMNode<T, U> node, TraverseContext<T, U> context) {
		ComplexSelectorPart part = node.getSelectorPart();
		if (part == null) return new HashSet<>(current);

		if (part instanceof TypeSelector typeSelector) {
			Set<T> allElementsWithName = context.typeMap().get(typeSelector.getQualifiedName().getName());
			Set<T> part1 = intersect(current, allElementsWithName, context);
			return filter(part1, participant -> elementMatch(participant, element -> isTypeMatch(element, typeSelector)));
		} else if (part instanceof IDSelector idSelector) {
			Set<T> allElementsWithID = context.idMap().get(idSelector.getId());
			Set<T> part1 = intersect(current, allElementsWithID, context);
			return filter(part1, participant -> elementMatch(participant, element -> element.getAttribute("id").equals(idSelector.getId())));
		} else if (part instanceof AttributeSelector classSelector && isClassSelector(classSelector)) {
			Set<T> allElementsWithClass = context.classMap().get(classSelector.getComparisonValue());
			Set<T> part1 = intersect(current, allElementsWithClass, context);
			Set<T> part2 = filter(part1, participant -> elementMatch(participant, element -> oneOf(element.getAttribute("class"), classSelector.getComparisonValue())));
			return part2;
		} else if (part instanceof AttributeSelector attributeSelector) {
			return filter(current, participant -> elementMatch(participant, element -> attributeMatches(element, attributeSelector)));
		}

		return Set.of();
	}

	private boolean elementMatch(T participant, Predicate<Element> filter) {
		if (!(participant instanceof DocumentStyleGenerator generator)) return false;
		Node domNode = generator.getDOMNode();
		if (!(domNode instanceof Element element)) return false;

		return filter.test(element);
	}

	private Set<T> intersect(Set<T> a, Set<T> b, TraverseContext<T, U> context) {
		if (a == null) return Set.of();
		if (b == null) return Set.of();
		if (b.size() > a.size()) {
			return intersect(b, a, context);
		}
		if (a == context.allParticipants() || a.containsAll(b)) {
			return b;
		}
		
		Set<T> result = new HashSet<>(b);
		b.retainAll(a);

		return result;
	}

	private Set<T> filter(Set<T> data, Predicate<T> filter) {
		if (data == null) return Set.of();
		Set<T> result = new HashSet<>();
		for (T item: data) {
			if (filter.test(item)) {
				result.add(item);
			}
		}
		return result;
	}

	private boolean isTypeMatch(Element element, TypeSelector typeSelector) {
		QualifiedName name = typeSelector.getQualifiedName();
		String namespace = name.getNamespace();
		String elementNamespace = element.getNamespace();

		if (!namespaceMatches(namespace, elementNamespace)) return false;
		if (!name.getName().equals(element.getLocalName())) return false;

		return true;
	}

	private boolean namespaceMatches(String namespace, String elementNamespace) {
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

	private boolean isClassSelector(AttributeSelector classSelector) {
		return
			namespaceMatches(classSelector.getAttributeName().getNamespace(), Namespace.HTML_NAMESPACE)
			&& classSelector.getAttributeName().getName().equals("class")
			&& classSelector.getOperation().equals(AttributeSelectorOperation.ONE_OF);
	}

	private boolean oneOf(String className, String comparisonValue) {
		if (className == null) return false;
		for (String classNamePart: className.split(" ")) {
			if (classNamePart.equals(comparisonValue)) {
				return true;
			}
		}
		return false;
	}

	private boolean attributeMatches(Element element, AttributeSelector attributeSelector) {
		System.out.println("Attribute selector: " + attributeSelector);
		return false;
	}

}
