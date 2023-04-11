package com.github.webicitybrowser.spiderhtml.test;

import com.github.webicitybrowser.spec.html.parse.tree.HTMLHtmlElementLeaf;

public class TestHTMLHtmlElementLeaf extends TestHTMLElementLeaf implements HTMLHtmlElementLeaf {

	public TestHTMLHtmlElementLeaf(TestHTMLDocumentLeaf nodeDocument) {
		super(nodeDocument, "html");
	}

}
