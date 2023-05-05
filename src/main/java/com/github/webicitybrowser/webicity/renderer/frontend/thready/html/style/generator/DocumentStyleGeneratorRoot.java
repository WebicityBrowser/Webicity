package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.function.Supplier;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMResult;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;

public class DocumentStyleGeneratorRoot implements StyleGeneratorRoot {
	
	private final Document document;
	private final Supplier<CSSOMTree<Node, DirectivePool>[]> cssomTreesSupplier;

	public DocumentStyleGeneratorRoot(Document document, Supplier<CSSOMTree<Node, DirectivePool>[]> cssomTreesSupplier) {
		this.document = document;
		this.cssomTreesSupplier = cssomTreesSupplier;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StyleGenerator generateChildStyleGenerator(ComponentUI componentUI) {
		CSSOMTree<Node, DirectivePool>[] cssomTrees = cssomTreesSupplier.get();
		CSSOMResult<Node, DirectivePool>[] results = (CSSOMResult<Node, DirectivePool>[]) new CSSOMResult[cssomTrees.length];
		for (int i = 0; i < cssomTrees.length; i++) {
			CSSOMTree<Node, DirectivePool> tree = cssomTrees[i];
			results[i] = tree.apply(new DocumentStyleParticipant(document));
		}
		
		return new DocumentStyleGenerator(((WebComponent) componentUI.getComponent()).getNode(), results);
	}

}
