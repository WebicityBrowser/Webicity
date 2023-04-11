package com.github.webicitybrowser.spiderhtml.test;

import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentLeaf;

public class TestHTMLDocumentLeaf extends TestHTMLLeaf implements HTMLDocumentLeaf {

	public TestHTMLDocumentLeaf() {
		super(null);
	}
	
	@Override
	public HTMLDocumentLeaf getNodeDocument() {
		return this;
	}

}
