package com.github.webicitybrowser.spiderhtml.test;

import com.github.webicitybrowser.spec.html.parse.ElementCreationOptions;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLElementLeaf;

public final class TestHTMLElementLeafCreator {

	public static HTMLElementLeaf create(ElementCreationOptions creationOptions) {
		String tagName = creationOptions.tagName();
		TestHTMLDocumentLeaf nodeDocument = (TestHTMLDocumentLeaf) creationOptions
			.intendedParent()
			.getNodeDocument();
		// TODO: Respect namespace
		switch (tagName) {
		case "html":
			return new TestHTMLHtmlElementLeaf(nodeDocument);
		default:
			return new TestHTMLElementLeaf(nodeDocument, tagName);
		}
	}

}
