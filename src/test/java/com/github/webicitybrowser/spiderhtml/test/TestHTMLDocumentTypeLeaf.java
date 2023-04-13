package com.github.webicitybrowser.spiderhtml.test;

import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentTypeLeaf;

public class TestHTMLDocumentTypeLeaf extends TestHTMLLeaf implements HTMLDocumentTypeLeaf {

	private String name;

	public TestHTMLDocumentTypeLeaf(TestHTMLDocumentLeaf nodeDocument, String name) {
		super(nodeDocument);
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

}
