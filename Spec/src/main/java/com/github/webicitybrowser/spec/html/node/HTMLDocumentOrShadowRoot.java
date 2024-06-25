package com.github.webicitybrowser.spec.html.node;

import com.github.webicitybrowser.spec.css.stylesheet.StyleSheetList;
import com.github.webicitybrowser.spec.dom.node.DocumentOrShadowRoot;

public interface HTMLDocumentOrShadowRoot extends DocumentOrShadowRoot {

	StyleSheetList getStyleSheets();
	
}
