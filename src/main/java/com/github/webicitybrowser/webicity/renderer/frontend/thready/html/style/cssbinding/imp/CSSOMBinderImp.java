package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import com.github.webicitybrowser.spec.css.componentvalue.SimpleBlock;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.imp.CSSParserImp;
import com.github.webicitybrowser.spec.css.parser.selectors.ComplexSelectorParser;
import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.spec.css.rule.QualifiedRule;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelector;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMBinder;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMFilterCreator;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator.DocumentStyleGenerator;

public class CSSOMBinderImp implements CSSOMBinder {
	
	private final CSSOMFilterCreator filterCreator = new CSSOMFilterCreatorImp();
	private final CSSOMDeclarationParser declarationParser = new CSSOMDeclarationParserImp();
	
	@Override
	public CSSOMTree<DocumentStyleGenerator, DirectivePool> createCSSOMFor(CSSRuleList ruleList) {
		CSSOMNode<DocumentStyleGenerator, DirectivePool> rootNode = CSSOMNode.create(null, null);
		addRuleListToCSSOMNode(rootNode, ruleList);
		
		return CSSOMTree.create(rootNode);
	}

	private void addRuleListToCSSOMNode(CSSOMNode<DocumentStyleGenerator, DirectivePool> rootNode, CSSRuleList ruleList) {
		for (int i = 0; i < ruleList.getLength(); i++) {
			CSSRule rule = ruleList.getItem(i);
			addCSSRuleToCSSOMNode(rootNode, rule, i);
		}
	}

	private void addCSSRuleToCSSOMNode(CSSOMNode<DocumentStyleGenerator, DirectivePool> rootNode, CSSRule rule, int order) {
		if (rule instanceof QualifiedRule qualifiedRule) {
			addQualifiedRuleToCSSOMNode(rootNode, qualifiedRule, order);
		}
	}

	private void addQualifiedRuleToCSSOMNode(CSSOMNode<DocumentStyleGenerator, DirectivePool> rootNode, QualifiedRule rule, int order) {
		TokenLike[] prelude = rule.getPrelude();
		ComplexSelector[] selectors = new ComplexSelectorParser().parseMany(prelude, order);
		for (ComplexSelector selector: selectors) {
			CSSOMNode<DocumentStyleGenerator, DirectivePool> targetNode = getSelectedCSSOMNode(rootNode, selector);
			DirectivePool properties = createPoolWithDeclarations(rule.getValue());
			targetNode.addNodeProperties(properties);
		}
	}

	private CSSOMNode<DocumentStyleGenerator, DirectivePool> getSelectedCSSOMNode(CSSOMNode<DocumentStyleGenerator, DirectivePool> rootNode, ComplexSelector selector) {
		CSSOMNode<DocumentStyleGenerator, DirectivePool> current = rootNode;
		for (ComplexSelectorPart complexSelectorPart: selector.getParts()) {
			CSSOMFilter<DocumentStyleGenerator, DirectivePool> filter = filterCreator.createFilterFor(complexSelectorPart);
			current = current.createChild(filter, 0);
		}
		current.setSpecificity(selector.getSpecificity());
		
		return current;
	}
	
	private DirectivePool createPoolWithDeclarations(SimpleBlock value) {
		CSSRule[] rules = parseDeclarations(value);
		DirectivePool directivePool = new BasicDirectivePool();
		for (CSSRule rule: rules) {
			if (!(rule instanceof Declaration)) {
				continue;
			}
			
			Directive[] declDirectives = declarationParser.parseDeclaration((Declaration) rule);
			if (declDirectives == null) {
				continue;
			}
			
			for (Directive declDirective: declDirectives) {
				directivePool.directive(declDirective);
			}
		}
		
		return directivePool;
	}



	private CSSRule[] parseDeclarations(SimpleBlock value) {
		return new CSSParserImp().parseAListOfDeclarations(value.getValue());
	}
	
}
