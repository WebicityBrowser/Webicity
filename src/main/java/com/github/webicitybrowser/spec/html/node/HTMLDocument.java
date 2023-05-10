package com.github.webicitybrowser.spec.html.node;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.html.node.imp.HTMLDocumentImp;

public interface HTMLDocument extends Document, HTMLDocumentOrShadowRoot {

	String getTitle();

	public static HTMLDocument create() {
		return new HTMLDocumentImp();
	}
	
}
