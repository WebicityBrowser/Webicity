package com.github.webicitybrowser.spec.html.binding;

import com.github.webicitybrowser.spec.dom.logic.ElementCreationLogic;
import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.DocumentType;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Text;
import com.github.webicitybrowser.spec.dom.node.imp.DocumentTypeImp;
import com.github.webicitybrowser.spec.dom.node.imp.TextImp;
import com.github.webicitybrowser.spec.html.node.imp.HTMLHtmlElementImp;
import com.github.webicitybrowser.spec.html.parse.ElementCreationOptions;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentTypeLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLElementLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLHtmlElementLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLTextLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLTreeBuilder;

public class BindingHTMLTreeBuilder implements HTMLTreeBuilder {
	
	private final BindingHTMLDocumentLeaf documentLeaf;

	public BindingHTMLTreeBuilder(Document document) {
		this.documentLeaf = new BindingHTMLDocumentLeaf(document);
	}

	@Override
	public HTMLDocumentLeaf getDocumentLeaf() {
		return this.documentLeaf;
	}

	@Override
	public HTMLHtmlElementLeaf createHtmlElementLeaf(HTMLDocumentLeaf nodeDocument) {
		return new BindingHTMLHtmlElementLeaf(nodeDocument, new HTMLHtmlElementImp());
	}

	@Override
	public HTMLElementLeaf createHtmlElementLeaf(ElementCreationOptions creationOptions) {
		Element element = ElementCreationLogic.createElement(creationOptions.tagName());
		return new BindingHTMLElementLeaf(documentLeaf, element);
	}

	@Override
	public HTMLDocumentTypeLeaf createDocumentTypeLeaf(String name, String publicId, String systemId) {
		DocumentType documentType = new DocumentTypeImp(name);
		return new BindingHTMLDocumentTypeLeaf(documentLeaf, documentType);
	}

	@Override
	public HTMLTextLeaf createTextLeaf() {
		Text text = new TextImp();
		return new BindingHTMLTextLeaf(documentLeaf, text);
	}

}
