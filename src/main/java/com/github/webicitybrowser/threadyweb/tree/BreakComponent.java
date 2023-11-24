package com.github.webicitybrowser.threadyweb.tree;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.threadyweb.tree.imp.BreakComponentImp;

public interface BreakComponent extends ElementComponent {
	
	public static BreakComponent create(Element element) {
		return new BreakComponentImp(element);
	}

}
