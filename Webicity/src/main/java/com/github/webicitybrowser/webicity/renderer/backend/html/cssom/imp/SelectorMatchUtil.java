package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.BitSet;
import java.util.function.Predicate;

import com.github.webicitybrowser.spec.css.QualifiedName;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.spec.css.selectors.combinator.Combinator;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector.AttributeSelectorOperation;
import com.github.webicitybrowser.spec.css.selectors.selector.IDSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.TypeSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.psuedo.PsuedoSelector;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTraverseContext;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator.DocumentStyleGenerator;

public class SelectorMatchUtil<T, U> {
	
	public BitSet matchIndividualSelectorPart(BitSet current, CSSOMNode<T, U> node, CSSOMTraverseContext<T, U> context) {
		ComplexSelectorPart part = node.getSelectorPart();
		if (part == null) return current;

		if (part instanceof TypeSelector typeSelector) {
			boolean matchAll = typeSelector.getQualifiedName().getName().equals(QualifiedName.ANY_NAME);
			BitSet allElementsWithName = matchAll ? context.allParticipantsBitSet() :
				context.typeMap().get(typeSelector.getQualifiedName().getName());
			BitSet part1 = intersect(current, allElementsWithName);
			return filter(part1, participant -> elementMatch(participant, element -> isTypeMatch(element, typeSelector)), context);
		} else if (part instanceof IDSelector idSelector) {
			BitSet allElementsWithID = context.idMap().get(idSelector.getId());
			BitSet part1 = intersect(current, allElementsWithID);
			return filter(part1, participant -> elementMatch(participant, element -> element.getAttribute("id").equals(idSelector.getId())), context);
		} else if (part instanceof AttributeSelector classSelector && isClassSelector(classSelector)) {
			BitSet allElementsWithClass = context.classMap().get(classSelector.getComparisonValue());
			BitSet part1 = intersect(current, allElementsWithClass);
			BitSet part2 = filter(part1, participant -> elementMatch(participant, element -> oneOf(element.getAttribute("class"), classSelector.getComparisonValue())), context);
			return part2;
		} else if (part instanceof AttributeSelector attributeSelector) {
			return filter(current, participant -> elementMatch(participant, element -> attributeMatches(element, attributeSelector)), context);
		} else if (part instanceof Combinator) {
			// Already processed
			return current;
		} else if (part instanceof PsuedoSelector psuedoSelector && psuedoSelector.getType().equals("root")) {
			return filter(current, participant -> elementMatch(participant, element ->
				element.getLocalName().equals("html") && element.getNamespace().equals(Namespace.HTML_NAMESPACE)
			), context);
	 	} else {
			throw new RuntimeException("Unknown selector part: " + part);
		}
	}

	private boolean elementMatch(T participant, Predicate<Element> filter) {
		if (!(participant instanceof DocumentStyleGenerator generator)) return false;
		Node domNode = generator.getDOMNode();
		if (!(domNode instanceof Element element)) return false;

		return filter.test(element);
	}

	private BitSet intersect(BitSet a, BitSet b) {
		if (a == null || b == null) return new BitSet();

		if (b.size() < a.size()) {
			return intersect(b, a);
		}
		BitSet result = (BitSet) a.clone();
		result.and(b);

		return result;
	}

	private BitSet filter(BitSet data, Predicate<T> filter, CSSOMTraverseContext<T, U> context) {
		if (data == null) return new BitSet();

		BitSet result = new BitSet();
		for (int i = data.nextSetBit(0); i >= 0; i = data.nextSetBit(i + 1)) {
			if (data.get(i) && filter.test(context.allParticipants().get(i))) {
				result.set(i);
			}
		}

		return result;
	}

	private boolean isTypeMatch(Element element, TypeSelector typeSelector) {
		QualifiedName name = typeSelector.getQualifiedName();
		String namespace = name.getNamespace();
		String elementNamespace = element.getNamespace();
		String elementName = element.getLocalName();

		if (!namespaceMatches(namespace, elementNamespace)) return false;
		if (name.getName().equals(QualifiedName.ANY_NAME)) return true;
		if (!name.getName().equals(elementName)) return false;

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
		String attrValue = element.getAttribute(attributeSelector.getAttributeName().getName());
		String comparisonValue = attributeSelector.getComparisonValue();
		if (attrValue == null) return false;
		switch (attributeSelector.getOperation()) {
			case BEGINS_WITH:
				return attrValue.startsWith(comparisonValue);
			case CONTAINS:
				return attrValue.contains(comparisonValue);
			case ENDS_WITH:
				return attrValue.endsWith(comparisonValue);
			case EQUALS:
				return attrValue.equals(comparisonValue);
			case HAS_SUBCODE:
				return attrValue.equals(comparisonValue) ||  attrValue.startsWith(comparisonValue + "-");
			case ONE_OF:
				throw new RuntimeException("This should be handled by AttributeOneOfFilter");
			case PRESENT:
				return true;
			default:
				throw new RuntimeException("Unknown operation: " + attributeSelector.getOperation());
		}
	}

}
