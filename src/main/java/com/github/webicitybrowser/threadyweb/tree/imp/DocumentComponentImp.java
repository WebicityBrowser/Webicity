package com.github.webicitybrowser.threadyweb.tree.imp;

import java.util.Optional;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.context.WebComponentContext;
import com.github.webicitybrowser.threadyweb.tree.DocumentComponent;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;

public class DocumentComponentImp extends BaseWebComponent implements DocumentComponent {

	private final Document document;
	private final WebComponentContext componentContext;
	
	private ElementComponent visibleChild;

	public DocumentComponentImp(Document document, WebComponentContext componentContext) {
		this.document = document;
		this.componentContext = componentContext;
		updateVisibleChild();
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
	public Optional<ElementComponent> getVisibleChild() {
		return Optional.ofNullable(visibleChild);
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

}
