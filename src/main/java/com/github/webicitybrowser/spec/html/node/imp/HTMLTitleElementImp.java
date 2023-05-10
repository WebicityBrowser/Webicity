package com.github.webicitybrowser.spec.html.node.imp;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.imp.util.DOMTextUtil;
import com.github.webicitybrowser.spec.html.node.HTMLTitleElement;

public class HTMLTitleElementImp extends HTMLElementImp implements HTMLTitleElement {

	public HTMLTitleElementImp(Document nodeDocument) {
		super(nodeDocument, "title");
	}

	@Override
	public String getText() {
		return DOMTextUtil.getChildTextContent(this);
	}

}
