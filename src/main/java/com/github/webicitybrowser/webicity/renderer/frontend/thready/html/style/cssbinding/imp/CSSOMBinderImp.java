package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import com.github.webicitybrowser.spec.css.componentvalue.SimpleBlock;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.imp.CSSParserImp;
import com.github.webicitybrowser.spec.css.parser.selectors.ComplexSelectorParser;
import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.CSSStyleSheet;
import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.spec.css.rule.QualifiedRule;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelector;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMBinder;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMFilterCreator;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.imp.CSSOMNodeImp;

public class CSSOMBinderImp implements CSSOMBinder {
	
	private final CSSOMFilterCreator filterCreator = new CSSOMFilterCreatorImp();
	private final CSSOMDeclarationParser declarationParser = new CSSOMDeclarationParserImp();
	
	@Override
	public CSSOMNode createCSSOMFor(CSSStyleSheet stylesheet) {
		CSSOMNode rootNode = new CSSOMNodeImp();
		addStyleSheetToCSSOMNode(rootNode, stylesheet);
		
		return rootNode;
	}

	private void addStyleSheetToCSSOMNode(CSSOMNode rootNode, CSSStyleSheet stylesheet) {
		for (CSSRule rule: stylesheet.getCSSRules()) {
			addCSSRuleToCSSOMNode(rootNode, rule);
		}
	}

	private void addCSSRuleToCSSOMNode(CSSOMNode rootNode, CSSRule rule) {
		if (rule instanceof QualifiedRule qualifiedRule) {
			addQualifiedRuleToCSSOMNode(rootNode, qualifiedRule);
		}
	}

	private void addQualifiedRuleToCSSOMNode(CSSOMNode rootNode, QualifiedRule rule) {
		TokenLike[] prelude = rule.getPrelude();
		ComplexSelector[] selectors = new ComplexSelectorParser().parseMany(prelude);
		for (ComplexSelector selector: selectors) {
			CSSOMNode targetNode = getSelectedCSSOMNode(rootNode, selector);
			addDeclarationsToCSSOMNode(targetNode, rule.getValue());
		}
	}

	private CSSOMNode getSelectedCSSOMNode(CSSOMNode rootNode, ComplexSelector selector) {
		CSSOMNode current = rootNode;
		for (ComplexSelectorPart complexSelectorPart: selector.getParts()) {
			CSSOMFilter filter = filterCreator.createFilterFor(complexSelectorPart);
			current = current.getChild(filter);
		}
		
		return current;
	}
	
	private void addDeclarationsToCSSOMNode(CSSOMNode targetNode, SimpleBlock value) {
		CSSRule[] rules = parseDeclarations(value);
		DirectivePool directivePool = new BasicDirectivePool();
		for (CSSRule rule: rules) {
			if (!(rule instanceof Declaration)) {
				continue;
			}
			
			Directive declDirective = declarationParser.parseDeclaration((Declaration) rule);
			if (declDirective == null) {
				continue;
			}
			
			directivePool.directive(declDirective);
		}
		targetNode.addDirectivePool(directivePool);
	}



	private CSSRule[] parseDeclarations(SimpleBlock value) {
		return new CSSParserImp().parseAListOfDeclarations(value.getValue());
	}
	
}
