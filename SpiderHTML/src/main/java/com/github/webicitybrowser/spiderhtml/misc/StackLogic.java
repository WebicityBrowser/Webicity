package com.github.webicitybrowser.spiderhtml.misc;

import java.util.Set;
import java.util.function.Function;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.html.node.HTMLElement;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;

public final class StackLogic {
	
	private static final Set<String> impliedEndTags = Set.of("dd", "dt", "li", "optgroup", "option", "p", "rb", "rp", "rt", "rtc");

	private static final Set<ElementQualifiedName> scopeTags = Set.of(
		new ElementQualifiedName(Namespace.HTML_NAMESPACE, "applet"),
		new ElementQualifiedName(Namespace.HTML_NAMESPACE, "caption"),
		new ElementQualifiedName(Namespace.HTML_NAMESPACE, "html"),
		new ElementQualifiedName(Namespace.HTML_NAMESPACE, "table"),
		new ElementQualifiedName(Namespace.HTML_NAMESPACE, "td"),
		new ElementQualifiedName(Namespace.HTML_NAMESPACE, "th"),
		new ElementQualifiedName(Namespace.HTML_NAMESPACE, "marquee"),
		new ElementQualifiedName(Namespace.HTML_NAMESPACE, "object"),
		new ElementQualifiedName(Namespace.HTML_NAMESPACE, "template"),
		new ElementQualifiedName(Namespace.MATHML_NAMESPACE, "mi"),
		new ElementQualifiedName(Namespace.MATHML_NAMESPACE, "mo"),
		new ElementQualifiedName(Namespace.MATHML_NAMESPACE, "mn"),
		new ElementQualifiedName(Namespace.MATHML_NAMESPACE, "ms"),
		new ElementQualifiedName(Namespace.MATHML_NAMESPACE, "mtext"),
		new ElementQualifiedName(Namespace.MATHML_NAMESPACE, "annotation-xml"),
		new ElementQualifiedName(Namespace.SVG_NAMESPACE, "foreignObject"),
		new ElementQualifiedName(Namespace.SVG_NAMESPACE, "desc"),
		new ElementQualifiedName(Namespace.SVG_NAMESPACE, "title")
	);

	private static final Set<ElementQualifiedName> buttonScopeTags = Set.of(
		new ElementQualifiedName(Namespace.HTML_NAMESPACE, "button")
	);

	private static final Set<ElementQualifiedName> tableScopeTags = Set.of(
		new ElementQualifiedName(Namespace.HTML_NAMESPACE, "html"),
		new ElementQualifiedName(Namespace.HTML_NAMESPACE, "table"),
		new ElementQualifiedName(Namespace.HTML_NAMESPACE, "template")
	);
	

	private StackLogic () {}

	public static void popUntil(ElementStack stack, String namespace, String name) {
		while (true) {
			Node node = stack.pop();
			if (
				node instanceof Element element &&
				element.getLocalName().equals(name) &&
				element.getNamespace().equals(namespace)
			) {
				break;
			}
		}
	}

	public static void generateImpliedEndTags(ElementStack stack, String... exceptions) {
		while (true) {
			Node currentNode = stack.peek();
			if (!(
				currentNode instanceof Element element &&
				element.getNamespace().equals(Namespace.HTML_NAMESPACE)
			)) {
				return;
			}
			
			String tagName = ((Element) currentNode).getLocalName();
			if (stringIs(tagName, exceptions) || !impliedEndTags.contains(tagName)) {
				return;
			}
			stack.pop();
		}
	}

	public static void closeAPElement(ElementStack stack, SharedContext context) {
		generateImpliedEndTags(stack, "p");
		if (!(stack.peek() instanceof HTMLElement element && element.getLocalName().equals("p"))) {
			context.recordError();
		}
		popUntil(stack, Namespace.HTML_NAMESPACE, "p");
	}

	public static boolean hasElementInScope(ElementStack stack, String element, String namespace) {
		return isElementInScope(
			stack, element, namespace,
			(qualifiedName) -> scopeTags.contains(qualifiedName));
	}

	public static boolean hasElementInButtonScope(ElementStack stack, String element, String namespace) {
		return isElementInScope(
			stack, element, namespace,
			(qualifiedName) -> scopeTags.contains(qualifiedName) || buttonScopeTags.contains(qualifiedName));
	}

	public static boolean hasElementInTableScope(ElementStack stack, String element, String namespace) {
		return isElementInScope(
			stack, element, namespace,
			(qualifiedName) -> tableScopeTags.contains(qualifiedName));
	}

	private static boolean stringIs(String name, String... names) {
		for (String matchName: names) {
			if (matchName.equals(name)) {
				return true;
			}
		}
		
		return false;
	}

	private static boolean isElementInScope(
		ElementStack stack, String element, String namespace, Function<ElementQualifiedName, Boolean> filter
	) {
		for (int pos = 0; pos < stack.size(); pos++) {
			Node node = stack.peek(pos);
			if (node instanceof Element elementNode) {
				if (
					elementNode.getLocalName().equals(element) &&
					elementNode.getNamespace().equals(namespace)
				) {
					return true;
				}
				if (filter.apply(new ElementQualifiedName(elementNode.getNamespace(), elementNode.getLocalName()))) {
					return false;
				}
			}
		}

		return false;
	}

	private static record ElementQualifiedName(String namespace, String name) {}
	
}
