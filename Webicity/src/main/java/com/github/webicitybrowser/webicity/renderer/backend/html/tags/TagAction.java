package com.github.webicitybrowser.webicity.renderer.backend.html.tags;

import com.github.webicitybrowser.spec.dom.node.Element;


public interface TagAction {

	default void onTagParsed(Element element) {
	}

}
