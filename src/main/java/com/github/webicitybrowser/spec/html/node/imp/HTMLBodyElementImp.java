package com.github.webicitybrowser.spec.html.node.imp;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.html.node.HTMLBodyElement;

public class HTMLBodyElementImp extends HTMLElementImp implements HTMLBodyElement {

	public HTMLBodyElementImp(Document nodeDocument) {
		super(nodeDocument, "body");
	}
	
}
