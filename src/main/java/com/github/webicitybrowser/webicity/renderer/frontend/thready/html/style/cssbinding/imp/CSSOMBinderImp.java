package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import java.util.Arrays;

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
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMBinder;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMFilterCreator;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMRuleMap;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator.DocumentStyleGenerator;

public class CSSOMBinderImp implements CSSOMBinder {
	
	private final CSSOMFilterCreator filterCreator = new CSSOMFilterCreatorImp();
	
	@Override
	public CSSOMTree<DocumentStyleGenerator, CSSOMRuleMap> createCSSOMFor(CSSRuleList ruleList) {
		CSSOMNode<DocumentStyleGenerator, CSSOMRuleMap> rootNode = CSSOMNode.create(null, null);
		addRuleListToCSSOMNode(rootNode, ruleList);
		
		return CSSOMTree.create(rootNode);
	}

	private void addRuleListToCSSOMNode(CSSOMNode<DocumentStyleGenerator, CSSOMRuleMap> rootNode, CSSRuleList ruleList) {
		for (int i = 0; i < ruleList.getLength(); i++) {
			CSSRule rule = ruleList.getItem(i);
			addCSSRuleToCSSOMNode(rootNode, rule, i);
		}
	}

	private void addCSSRuleToCSSOMNode(CSSOMNode<DocumentStyleGenerator, CSSOMRuleMap> rootNode, CSSRule rule, int order) {
		if (rule instanceof QualifiedRule qualifiedRule) {
			addQualifiedRuleToCSSOMNode(rootNode, qualifiedRule, order);
		}
	}

	private void addQualifiedRuleToCSSOMNode(CSSOMNode<DocumentStyleGenerator, CSSOMRuleMap> rootNode, QualifiedRule rule, int order) {
		TokenLike[] prelude = rule.getPrelude();
		ComplexSelector[] selectors = new ComplexSelectorParser().parseMany(prelude, order);
		for (ComplexSelector selector: selectors) {
			CSSOMNode<DocumentStyleGenerator, CSSOMRuleMap> targetNode = getSelectedCSSOMNode(rootNode, selector);
			CSSOMRuleMap properties = createCSSOMRuleMap(rule.getValue());
			targetNode.addNodeProperties(properties);
		}
	}

	private CSSOMNode<DocumentStyleGenerator, CSSOMRuleMap> getSelectedCSSOMNode(
		CSSOMNode<DocumentStyleGenerator, CSSOMRuleMap> rootNode, ComplexSelector selector
	) {
		CSSOMNode<DocumentStyleGenerator, CSSOMRuleMap> current = rootNode;
		for (ComplexSelectorPart complexSelectorPart: selector.getParts()) {
			CSSOMFilter<DocumentStyleGenerator, CSSOMRuleMap> filter = filterCreator.createFilterFor(complexSelectorPart);
			current = current.createChild(filter, 0);
		}
		current.setSpecificity(selector.getSpecificity());
		
		return current;
	}
	
	private CSSOMRuleMap createCSSOMRuleMap(SimpleBlock value) {
		CSSRule[] rules = parseDeclarations(value);
		Declaration[] declarations = Arrays.stream(rules)
			.filter(rule -> rule instanceof Declaration)
			.map(rule -> (Declaration) rule)
			.toArray(Declaration[]::new);
		return CSSOMRuleMap.create(declarations);
	}

	private CSSRule[] parseDeclarations(SimpleBlock value) {
		return new CSSParserImp().parseAListOfDeclarations(value.getValue());
	}
	
}
