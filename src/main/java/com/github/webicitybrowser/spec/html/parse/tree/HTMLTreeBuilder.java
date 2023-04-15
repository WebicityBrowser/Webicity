package com.github.webicitybrowser.spec.html.parse.tree;

import com.github.webicitybrowser.spec.html.parse.ElementCreationOptions;

public interface HTMLTreeBuilder {

	HTMLDocumentLeaf getDocumentLeaf();

	HTMLHtmlElementLeaf createHtmlElementLeaf(HTMLDocumentLeaf nodeDocument);

	HTMLElementLeaf createHtmlElementLeaf(ElementCreationOptions creationOptions);

	HTMLDocumentTypeLeaf createDocumentTypeLeaf(String name, String publicId, String systemId);

	HTMLTextLeaf createTextLeaf();
	
}
