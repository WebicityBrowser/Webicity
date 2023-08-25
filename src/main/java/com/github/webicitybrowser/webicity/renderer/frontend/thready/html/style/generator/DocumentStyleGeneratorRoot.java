package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.function.Supplier;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;

public class DocumentStyleGeneratorRoot implements StyleGeneratorRoot {
	
	private final Document document;
	private final Supplier<CSSOMTree<DocumentStyleGenerator, DirectivePool>[]> cssomTreesSupplier;

	public DocumentStyleGeneratorRoot(Document document, Supplier<CSSOMTree<DocumentStyleGenerator, DirectivePool>[]> cssomTreesSupplier) {
		this.document = document;
		this.cssomTreesSupplier = cssomTreesSupplier;
	}

	@Override
	public StyleGenerator generateChildStyleGenerator(ComponentUI componentUI) {
		DocumentStyleGenerator rootGenerator = new DocumentStyleGenerator(document, null);

		CSSOMTree<DocumentStyleGenerator, DirectivePool>[] cssomTrees = cssomTreesSupplier.get();
		for (CSSOMTree<DocumentStyleGenerator, DirectivePool> tree: cssomTrees) {
			tree.apply(rootGenerator, new DocumentParticipantTraverser());
		}
		rootGenerator.generateStyleDirectives(null, componentUI.getComponent().getStyleDirectives());
		
		return rootGenerator;
	}

}
