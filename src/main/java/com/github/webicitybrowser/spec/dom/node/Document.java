package com.github.webicitybrowser.spec.dom.node;

import com.github.webicitybrowser.spec.dom.node.imp.DocumentImp;

public interface Document extends Node, DocumentOrShadowRoot {

	public static Document create() {
		return new DocumentImp();
	}
	
}
