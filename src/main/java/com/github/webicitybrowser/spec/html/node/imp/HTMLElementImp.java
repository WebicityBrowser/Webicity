package com.github.webicitybrowser.spec.html.node.imp;

import com.github.webicitybrowser.spec.dom.node.imp.ElementImp;
import com.github.webicitybrowser.spec.html.node.HTMLElement;
import com.github.webicitybrowser.spec.infra.Namespace;

public class HTMLElementImp extends ElementImp implements HTMLElement {

	public HTMLElementImp(String localTag) {
		super(Namespace.HTML_NAMESPACE, localTag);
	}

}
