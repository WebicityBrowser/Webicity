package com.github.webicitybrowser.spiderhtml.test;

import com.github.webicitybrowser.spec.html.parse.ElementCreationOptions;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLElementLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLHtmlElementLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLTreeBuilder;

public class TestHTMLTreeBuilder implements HTMLTreeBuilder {
	
	private final TestHTMLDocumentLeaf documentLeaf = new TestHTMLDocumentLeaf();

	@Override
	public TestHTMLDocumentLeaf getDocument() {
		return documentLeaf;
	}

	@Override
	public HTMLHtmlElementLeaf createHtmlElementLeaf(HTMLDocumentLeaf nodeDocument) {
		return new TestHTMLHtmlElementLeaf((TestHTMLDocumentLeaf) nodeDocument);
	}

	@Override
	public HTMLElementLeaf createHtmlElementLeaf(ElementCreationOptions creationOptions) {
		return TestHTMLElementLeafCreator.create(creationOptions);
	}

}
