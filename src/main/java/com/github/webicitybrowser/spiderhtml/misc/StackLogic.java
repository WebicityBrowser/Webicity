package com.github.webicitybrowser.spiderhtml.misc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.infra.Namespace;

public final class StackLogic {
	
	private static final Set<String> impliedEndTags = new HashSet<>(
		List.of("dd", "dt", "li", "optgroup", "option", "p", "rb", "rp", "rt", "rtc"));

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
	
	private static boolean stringIs(String name, String... names) {
		for (String matchName: names) {
			if (matchName.equals(name)) {
				return true;
			}
		}
		
		return false;
	}
	
}
