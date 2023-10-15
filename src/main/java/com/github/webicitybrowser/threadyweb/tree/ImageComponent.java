package com.github.webicitybrowser.threadyweb.tree;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.threadyweb.context.WebComponentContext;
import com.github.webicitybrowser.threadyweb.tree.imp.image.ImageWebComponent;

public interface ImageComponent extends WebComponent {

	static ImageComponent create(Element element, WebComponentContext componentContext) {
		return new ImageWebComponent(element, componentContext);
	}
	
}
