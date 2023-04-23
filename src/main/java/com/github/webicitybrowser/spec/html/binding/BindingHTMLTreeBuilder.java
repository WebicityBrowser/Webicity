package com.github.webicitybrowser.spec.html.binding;

import com.github.webicitybrowser.spec.dom.logic.ElementCreationLogic;
import com.github.webicitybrowser.spec.dom.node.Comment;
import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.DocumentType;
import com.github.webicitybrowser.spec.dom.node.Text;
import com.github.webicitybrowser.spec.dom.node.imp.CommentImp;
import com.github.webicitybrowser.spec.dom.node.imp.DocumentTypeImp;
import com.github.webicitybrowser.spec.dom.node.imp.TextImp;
import com.github.webicitybrowser.spec.html.node.HTMLElement;
import com.github.webicitybrowser.spec.html.node.HTMLHtmlElement;
import com.github.webicitybrowser.spec.html.node.imp.HTMLHtmlElementImp;
import com.github.webicitybrowser.spec.html.parse.ElementCreationOptions;
import com.github.webicitybrowser.spec.html.parse.HTMLTreeBuilder;

public class BindingHTMLTreeBuilder implements HTMLTreeBuilder {

	private final Document document;

	public BindingHTMLTreeBuilder(Document document) {
		this.document = document;
	}

	@Override
	public Document getDocument() {
		return this.document;
	}

	@Override
	public HTMLHtmlElement createHtmlElement(Document nodeDocument) {
		return new HTMLHtmlElementImp(nodeDocument);
	}

	@Override
	public HTMLElement createHtmlElement(ElementCreationOptions creationOptions) {
		return (HTMLElement) ElementCreationLogic.createElement(document, creationOptions.tagName());
	}

	@Override
	public DocumentType createDocumentType(String name, String publicId, String systemId) {
		return new DocumentTypeImp(document, name);
	}

	@Override
	public Text createTextNode() {
		return new TextImp(document);
	}

	@Override
	public Comment createComment(String data, Document nodeDocument) {
		return new CommentImp(nodeDocument, data);
	}

}
