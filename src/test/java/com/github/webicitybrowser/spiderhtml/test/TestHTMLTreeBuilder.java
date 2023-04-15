package com.github.webicitybrowser.spiderhtml.test;

import com.github.webicitybrowser.spec.html.parse.ElementCreationOptions;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentTypeLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLElementLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLHtmlElementLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLTextLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLTreeBuilder;

public class TestHTMLTreeBuilder implements HTMLTreeBuilder {
	
	private final TestHTMLDocumentLeaf documentLeaf = new TestHTMLDocumentLeaf();

	@Override
	public TestHTMLDocumentLeaf getDocumentLeaf() {
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

	@Override
	public HTMLDocumentTypeLeaf createDocumentTypeLeaf(String name, String publicId, String systemId) {
		// TODO: Should nodeDocument be passed as an argument? The spec doesn't explicitly specify the nodeDocument
		return new TestHTMLDocumentTypeLeaf(documentLeaf, name);
	}

	@Override
	public HTMLTextLeaf createTextLeaf() {
		return new TestHTMLTextLeaf(documentLeaf);
	}

}
