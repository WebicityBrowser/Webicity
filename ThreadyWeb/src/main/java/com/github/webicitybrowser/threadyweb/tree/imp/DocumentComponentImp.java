package com.github.webicitybrowser.threadyweb.tree.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.html.node.HTMLDocument;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.context.WebComponentContext;
import com.github.webicitybrowser.threadyweb.tree.DocumentComponent;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;

public class DocumentComponentImp extends BaseWebComponent implements DocumentComponent {

	private final HTMLDocument document;
	private final WebComponentContext componentContext;
	private final List<Runnable> stylesheetsChangedListeners;
	
	
	private ElementComponent visibleChild;

	public DocumentComponentImp(HTMLDocument document, WebComponentContext componentContext) {
		this.document = document;
		this.componentContext = componentContext;
		this.stylesheetsChangedListeners = new ArrayList<>();
		updateVisibleChild();
		setupStylesheetsChangedListener();
	}

	@Override
	public Class<? extends Component> getPrimaryType() {
		return DocumentComponent.class;
	}
	
	@Override
	public Node getNode() {
		return document;
	}

	@Override
	public Optional<Component> getVisibleChild() {
		return Optional.ofNullable(visibleChild);
	}

	@Override
	public void addStylesheetsChangedListener(Runnable listener) {
		stylesheetsChangedListeners.add(listener);
	}
	
	private void updateVisibleChild() {
		Element foundChild = findElementChild();
		if (foundChild == null) {
			visibleChild = null;
			return;
		}
		
		// TODO: Only create new component if node mismatches existing component's node
		this.visibleChild = new ElementComponentImp(foundChild, componentContext);
	}

	private Element findElementChild() {
		for (Node node: document.getChildNodes()) {
			if (node instanceof Element) {
				return (Element) node;
			}
		}
		return null;
	}

	private void setupStylesheetsChangedListener() {
		document.getStyleSheets().addUpdateListener(() -> {
			stylesheetsChangedListeners.forEach(Runnable::run);
		});
	}

}
