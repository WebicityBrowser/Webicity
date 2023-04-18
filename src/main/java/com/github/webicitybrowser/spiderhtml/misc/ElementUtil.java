package com.github.webicitybrowser.spiderhtml.misc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.infra.Namespace;

public final class ElementUtil {
	
	private static final Set<String> special = new HashSet<>(List.of(
		"address", "applet", "area", "article", "aside", "base", "basefont", "bgsound",
		"blockquote", "body", "br", "button", "caption", "center", "col", "colgroup", "dd",
		"details", "dir", "div", "dl", "dt", "embed", "fieldset", "figcaption", "figure", "footer",
		"form", "frame", "frameset", "h1", "h2", "h3", "h4", "h5", "h6", "head", "header", "hgroup",
		"hr", "html", "iframe", "img", "input", "keygen", "li", "link", "listing", "main", "marquee",
		"menu", "meta", "nav", "noembed", "noframes", "noscript", "object", "ol", "p", "param",
		"plaintext", "pre", "script", "search", "section", "select", "source", "style", "summary",
		"table", "tbody", "td", "template", "textarea", "tfoot", "th", "thead", "title", "tr", "track",
		"ul", "wbr", "xmp"));

	private ElementUtil() {}

	public static boolean isHTMLElementWithName(Node node, String name) {
		return
			node instanceof Element element &&
			element.getLocalName().equals(name) &&
			element.getNamespace().equals(Namespace.HTML_NAMESPACE);
	}

	public static boolean isSpecial(Element element) {
		// TODO: Support elements in other namespaces
		switch (element.getNamespace()) {
		case Namespace.HTML_NAMESPACE:
			return special.contains(element.getLocalName());
		default:
			return false;
		}
	}
	
}
