package com.github.webicitybrowser.spec.dom.logic;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.html.node.imp.HTMLBodyElementImp;
import com.github.webicitybrowser.spec.html.node.imp.HTMLElementImp;
import com.github.webicitybrowser.spec.html.node.imp.HTMLHeadElementImp;
import com.github.webicitybrowser.spec.html.node.imp.HTMLHtmlElementImp;

public final class ElementCreationLogic {

	private ElementCreationLogic() {}
	
	public static Element createElement(Document nodeDocument, String name) {
		// TODO: Actual logic
		switch (name) {
		case "html":
			return new HTMLHtmlElementImp(nodeDocument);
		case "head":
			return new HTMLHeadElementImp(nodeDocument);
		case "body":
			return new HTMLBodyElementImp(nodeDocument);
		default:
			return new HTMLElementImp(nodeDocument, name);
		}
	}
	
}
