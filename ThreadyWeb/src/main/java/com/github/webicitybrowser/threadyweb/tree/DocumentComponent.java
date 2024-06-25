package com.github.webicitybrowser.threadyweb.tree;

import java.util.Optional;

import com.github.webicitybrowser.spec.html.node.HTMLDocument;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.context.WebComponentContext;
import com.github.webicitybrowser.threadyweb.tree.imp.DocumentComponentImp;

public interface DocumentComponent extends WebComponent {
	
	Optional<Component> getVisibleChild();

	void addStylesheetsChangedListener(Runnable listener);

	public static DocumentComponent create(HTMLDocument document, WebComponentContext componentContext) {
		return new DocumentComponentImp(document, componentContext);
	}
	
}
