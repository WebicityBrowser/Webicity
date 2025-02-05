package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.DirectiveDeriver;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMMappedRuleList;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMMappedRuleList.PropertyMapper;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTraverseContext;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTraverseContextGenerator;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;

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

		Map<CSSRuleList, CSSOMMappedRuleList<Directive>> mappedRuleListCache = new HashMap<>();
		DocumentPropertyMapper propertyMapper = new DocumentPropertyMapper();
		DocumentStyleGenerator rootGenerator = new DocumentStyleGenerator(
			document, null,
			ruleList -> mappedRuleListCache.computeIfAbsent(ruleList, key -> CSSOMMappedRuleList.create(ruleList, propertyMapper)),
			derivers);

		CSSOMTree<DocumentStyleGenerator, CSSRuleList>[] cssomTrees = cssomTreesSupplier.get();
		CSSOMTraverseContext<DocumentStyleGenerator, CSSRuleList> traverseContext = CSSOMTraverseContextGenerator
			.<DocumentStyleGenerator, CSSRuleList>create()
			.apply(rootGenerator, new DocumentParticipantTraverser());
		for (CSSOMTree<DocumentStyleGenerator, CSSRuleList> tree: cssomTrees) {
			tree.apply(traverseContext);
		}

		// TODO: Allow rules from component

		if (componentUI.getComponent() instanceof ElementComponent elementComponent) {
			CSSRuleList componentRules = elementComponent.getComponentRules();
			rootGenerator.generateStyleDirectives(componentRules);
		} else {
			rootGenerator.generateStyleDirectives(CSSRuleList.createEmpty());
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

	private static class DocumentPropertyMapper implements PropertyMapper<Directive> {

		private final CSSOMDeclarationParser parser = CSSOMDeclarationParser.create();

		@Override
		public List<Class<? extends Directive>> getPossibleResultantTypes(String name) {
			CSSOMNamedDeclarationParser<?> namedParser = this.parser.getNamedDeclarationParser(name);
			if (namedParser == null) return List.of();
			return namedParser.getResultantDirectiveClasses();
		}

		@Override
		public List<Directive> map(String name, TokenLike[] tokens) {
			return List.of(parser.parseDeclaration(name, tokens));
		}

		@Override
		public Class<? extends Directive> keyForValue(Directive value) {
			return value.getPrimaryType();
		}
		
	}

}
