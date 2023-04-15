package com.github.webicitybrowser.spiderhtml.test;

import com.github.webicitybrowser.spec.html.parse.tree.HTMLTextLeaf;

public class TestHTMLTextLeaf extends TestHTMLLeaf implements HTMLTextLeaf {
	
	private final StringBuilder text = new StringBuilder();

	public TestHTMLTextLeaf(TestHTMLDocumentLeaf nodeDocument) {
		super(nodeDocument);
	}

	@Override
	public void appendData(String string) {
		text.append(string);
	}
	
	public String getData() {
		return text.toString();
	}
	
}