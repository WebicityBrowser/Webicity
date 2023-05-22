package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import java.util.List;

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
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp.CSSOMNodeImp;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMBinder;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMFilterCreator;

public class CSSOMBinderImp implements CSSOMBinder {
	
	private final CSSOMFilterCreator filterCreator = new CSSOMFilterCreatorImp();
	private final CSSOMDeclarationParser declarationParser = new CSSOMDeclarationParserImp();
	
	@Override
	public CSSOMTree<Node, DirectivePool> createCSSOMFor(CSSRuleList ruleList) {
		CSSOMNode<Node, DirectivePool> rootNode = new CSSOMNodeImp<>();
		addRuleListToCSSOMNode(rootNode, ruleList);
		rootNode.linkChild((_1, node, traverser) -> List.of(traverser.getChildren(node)), 0, rootNode);
		
		return CSSOMTree.create(rootNode);
	}

	private void addRuleListToCSSOMNode(CSSOMNode<Node, DirectivePool> rootNode, CSSRuleList ruleList) {
		for (int i = 0; i < ruleList.getLength(); i++) {
			CSSRule rule = ruleList.getItem(i);
			addCSSRuleToCSSOMNode(rootNode, rule);
		}
	}

	private void addCSSRuleToCSSOMNode(CSSOMNode<Node, DirectivePool> rootNode, CSSRule rule) {
		if (rule instanceof QualifiedRule qualifiedRule) {
			addQualifiedRuleToCSSOMNode(rootNode, qualifiedRule);
		}
	}

	private void addQualifiedRuleToCSSOMNode(CSSOMNode<Node, DirectivePool> rootNode, QualifiedRule rule) {
		TokenLike[] prelude = rule.getPrelude();
		ComplexSelector[] selectors = new ComplexSelectorParser().parseMany(prelude);
		for (ComplexSelector selector: selectors) {
			CSSOMNode<Node, DirectivePool> targetNode = getSelectedCSSOMNode(rootNode, selector);
			DirectivePool properties = createPoolWithDeclarations(rule.getValue());
			targetNode.addNodeProperties(properties);
		}
	}

	private CSSOMNode<Node, DirectivePool> getSelectedCSSOMNode(CSSOMNode<Node, DirectivePool> rootNode, ComplexSelector selector) {
		CSSOMNode<Node, DirectivePool> current = rootNode;
		for (ComplexSelectorPart complexSelectorPart: selector.getParts()) {
			CSSOMFilter<Node, DirectivePool> filter = filterCreator.createFilterFor(complexSelectorPart);
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
