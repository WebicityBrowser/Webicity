package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.DirectiveDeriver;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMDeclarationParser;

public class DocumentStyleGeneratorRoot implements StyleGeneratorRoot {
	
	private final Document document;
	private final Supplier<CSSOMTree<DocumentStyleGenerator, CSSRuleList>[]> cssomTreesSupplier;
	private final Function<StyleContext, List<DirectiveDeriver<? extends Directive>>> deriversGenerator;

	public DocumentStyleGeneratorRoot(
		Document document, Supplier<CSSOMTree<DocumentStyleGenerator, CSSRuleList>[]> cssomTreesSupplier,
		Function<StyleContext, List<DirectiveDeriver<? extends Directive>>> deriversGenerator
	) {
		this.document = document;
		this.cssomTreesSupplier = cssomTreesSupplier;
		this.deriversGenerator = deriversGenerator;
	}

	@Override
	public StyleGenerator generateChildStyleGenerator(ComponentUI componentUI, StyleContext styleContext) {
		List<DirectiveDeriver<? extends Directive>> deriversList = deriversGenerator.apply(styleContext);
		Map<Class<? extends Directive>, DirectiveDeriver<? extends Directive>> derivers = formatDerivers(deriversList);
		DocumentStyleGenerator rootGenerator = new DocumentStyleGenerator(document, null, CSSOMDeclarationParser.create(), derivers);

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

	private Map<Class<? extends Directive>, DirectiveDeriver<? extends Directive>> formatDerivers(
		List<DirectiveDeriver<? extends Directive>> deriversList
	) {
		return deriversList.stream().collect(
			Collectors.toMap(
				DirectiveDeriver::getDerivedType,
				deriver -> deriver
			)
		);
	}

}
