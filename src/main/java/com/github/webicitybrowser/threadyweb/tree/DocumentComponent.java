package com.github.webicitybrowser.threadyweb.tree;

import java.util.Optional;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.threadyweb.context.WebComponentContext;
import com.github.webicitybrowser.threadyweb.tree.imp.DocumentComponentImp;

public interface DocumentComponent extends WebComponent {
	
	Optional<ElementComponent> getVisibleChild();

	public static DocumentComponent create(Document document, WebComponentContext componentContext) {
		return new DocumentComponentImp(document, componentContext);
	}
	
}
