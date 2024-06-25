package com.github.webicitybrowser.spec.html.parse;

import com.github.webicitybrowser.spec.dom.node.Comment;
import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.DocumentType;
import com.github.webicitybrowser.spec.dom.node.Text;
import com.github.webicitybrowser.spec.html.node.HTMLElement;
import com.github.webicitybrowser.spec.html.node.HTMLHtmlElement;

public interface HTMLTreeBuilder {

	Document getDocument();

	HTMLHtmlElement createHtmlElement(Document nodeDocument);

	HTMLElement createHtmlElement(ElementCreationOptions creationOptions);

	DocumentType createDocumentType(String name, String publicId, String systemId);

	Text createTextNode();

	Comment createComment(String data, Document nodeDocument);
	
}
