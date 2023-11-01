package com.github.webicitybrowser.threadyweb.tree;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.threadyweb.context.WebComponentContext;
import com.github.webicitybrowser.threadyweb.tree.imp.ElementComponentImp;

public interface ElementComponent extends WebComponent {

	WebComponent[] getChildren();

	public static WebComponent create(Element element, WebComponentContext componentContext) {
		return new ElementComponentImp(element, componentContext);
	}

}
