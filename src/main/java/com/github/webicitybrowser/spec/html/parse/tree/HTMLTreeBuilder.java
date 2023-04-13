package com.github.webicitybrowser.spec.html.parse.tree;

import com.github.webicitybrowser.spec.html.parse.ElementCreationOptions;

public interface HTMLTreeBuilder {

	HTMLDocumentLeaf getDocument();

	HTMLHtmlElementLeaf createHtmlElementLeaf(HTMLDocumentLeaf nodeDocument);

	HTMLElementLeaf createHtmlElementLeaf(ElementCreationOptions creationOptions);

	HTMLDocumentTypeLeaf createDocumentTypeLeaf(String name, String publicId, String systemId);
	
}
