package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.function.Supplier;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMDeclarationParser;

public class DocumentStyleGeneratorRoot implements StyleGeneratorRoot {
	
	private final Document document;
	private final Supplier<CSSOMTree<DocumentStyleGenerator, CSSRuleList>[]> cssomTreesSupplier;

	public DocumentStyleGeneratorRoot(Document document, Supplier<CSSOMTree<DocumentStyleGenerator, CSSRuleList>[]> cssomTreesSupplier) {
		this.document = document;
		this.cssomTreesSupplier = cssomTreesSupplier;
	}

	@Override
	public StyleGenerator generateChildStyleGenerator(ComponentUI componentUI) {
		DocumentStyleGenerator rootGenerator = new DocumentStyleGenerator(document, null, CSSOMDeclarationParser.create());

		CSSOMTree<DocumentStyleGenerator, CSSRuleList>[] cssomTrees = cssomTreesSupplier.get();
		for (CSSOMTree<DocumentStyleGenerator, CSSRuleList> tree: cssomTrees) {
			tree.apply(rootGenerator, new DocumentParticipantTraverser());
		}

		// TODO: Allow rules from component

		if (componentUI.getComponent() instanceof ElementComponent elementComponent) {
			CSSRuleList componentRules = elementComponent.getComponentRules();
			rootGenerator.generateStyleDirectives(null, componentRules);
		} else {
			rootGenerator.generateStyleDirectives(null, CSSRuleList.createEmpty());
		}
		
		return rootGenerator;
	}

}
