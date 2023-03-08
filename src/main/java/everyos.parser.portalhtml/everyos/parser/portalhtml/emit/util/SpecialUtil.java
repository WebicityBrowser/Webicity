package everyos.parser.portalhtml.emit.util;

import everyos.parser.portalhtml.tree.HTMLElementLeaf;

public final class SpecialUtil {
	
	private static final String[] specialHTMLElements = new String[] {
		"address", "applet", "area", "article", "aside", "base", "basefont", "bgsound", "blockquote",
		"body", "br", "button", "caption", "center", "col", "colgroup", "dd", "details", "dir", "div",
		"dl", "dt", "embed", "fieldset", "figcaption", "figure", "footer", "form", "frame", "frameset",
		"h1", "h2", "h3", "h4", "h5", "h6", "head", "header", "hgroup", "hr", "html", "iframe", "img",
		"input", "keygen", "li", "link", "listing", "main", "marquee", "menu", "meta", "nav", "noembed",
		"noframes", "noscript", "object", "ol", "p", "param", "plaintext", "pre", "script", "section",
		"select", "source", "style", "summary", "table", "tbody", "td", "template", "textarea", "tfoot",
		"th", "thead", "title", "tr", "track", "ul", "wbr", "xmp"
	};

	private SpecialUtil() {}

	public static boolean isSpecial(HTMLElementLeaf element) {
		switch (element.getNamespace()) {
		case TreeUtil.HTML_NAMESPACE:
			return isSpecialHTML(element.getLocalName());
		default:
			return false;
		}
	}

	private static boolean isSpecialHTML(String localName) {
		for (String name: specialHTMLElements) {
			if (name.equals(localName)) {
				return true;
			}
		}
		
		return false;
	}
	
}
