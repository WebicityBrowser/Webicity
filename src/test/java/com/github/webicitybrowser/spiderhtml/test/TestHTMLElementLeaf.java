package com.github.webicitybrowser.spiderhtml.test;

import com.github.webicitybrowser.spec.html.parse.tree.HTMLElementLeaf;

public class TestHTMLElementLeaf extends TestHTMLLeaf implements HTMLElementLeaf {

	private String name;

	public TestHTMLElementLeaf(TestHTMLDocumentLeaf nodeDocument, String name) {
		super(nodeDocument);
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
}
