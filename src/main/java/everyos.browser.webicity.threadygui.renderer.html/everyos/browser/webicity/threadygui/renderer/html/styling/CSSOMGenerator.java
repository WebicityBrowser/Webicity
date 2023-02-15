package everyos.browser.webicity.threadygui.renderer.html.styling;

import everyos.browser.webicity.threadygui.renderer.html.cssom.CSSOMFilter;
import everyos.browser.webicity.threadygui.renderer.html.cssom.CSSOMNode;
import everyos.browser.webicity.threadygui.renderer.html.cssom.declaration.DeclarationParser;
import everyos.browser.webicity.threadygui.renderer.html.cssom.imp.CSSOMNodeImp;
import everyos.desktop.thready.core.gui.directive.Directive;
import everyos.web.spec.css.componentvalue.SimpleBlock;
import everyos.web.spec.css.parser.TokenLike;
import everyos.web.spec.css.parser.imp.CSSParserImp;
import everyos.web.spec.css.parser.selectors.ComplexSelectorParser;
import everyos.web.spec.css.rule.CSSRule;
import everyos.web.spec.css.rule.CSSStyleSheet;
import everyos.web.spec.css.rule.Declaration;
import everyos.web.spec.css.rule.QualifiedRule;
import everyos.web.spec.css.selectors.ComplexSelector;
import everyos.web.spec.css.selectors.ComplexSelectorPart;

public final class CSSOMGenerator {

	private CSSOMGenerator() {}

	public static CSSOMNode generateCSSOM(CSSStyleSheet[] stylesheets) {
		CSSOMNode rootNode = new CSSOMNodeImp();
		for (CSSStyleSheet stylesheet: stylesheets) {
			addStyleSheetToCSSOMNode(rootNode, stylesheet);
		}
		
		return rootNode;
	}

	private static void addStyleSheetToCSSOMNode(CSSOMNode rootNode, CSSStyleSheet stylesheet) {
		for (CSSRule rule: stylesheet.getCSSRules()) {
			addCSSRuleToCSSOMNode(rootNode, rule);
		}
	}

	private static void addCSSRuleToCSSOMNode(CSSOMNode rootNode, CSSRule rule) {
		if (rule instanceof QualifiedRule qualifiedRule) {
			addQualifiedRuleToCSSOMNode(rootNode, qualifiedRule);
		}
	}

	private static void addQualifiedRuleToCSSOMNode(CSSOMNode rootNode, QualifiedRule rule) {
		TokenLike[] prelude = rule.getPrelude();
		ComplexSelector[] selectors = new ComplexSelectorParser().parseMany(prelude);
		for (ComplexSelector selector: selectors) {
			CSSOMNode targetNode = getSelectedCSSOMNode(rootNode, selector);
			addDeclarationsToCSSOMNode(targetNode, rule.getValue());
		}
	}

	private static CSSOMNode getSelectedCSSOMNode(CSSOMNode rootNode, ComplexSelector selector) {
		CSSOMNode current = rootNode;
		for (ComplexSelectorPart complexSelectorPart: selector.getParts()) {
			CSSOMFilter filter = FilterCreator.createFilterFor(complexSelectorPart);
			current = current.getChild(filter);
		}
		
		return current;
	}
	
	private static void addDeclarationsToCSSOMNode(CSSOMNode targetNode, SimpleBlock value) {
		CSSRule[] rules = parseDeclarations(value);
		for (CSSRule rule: rules) {
			if (!(rule instanceof Declaration)) {
				continue;
			}
			
			Directive declDirective = DeclarationParser.parseDeclaration((Declaration) rule);
			if (declDirective == null) {
				continue;
			}
			
			targetNode
				.getDirectivePool()
				.directive(declDirective);
		}
	}



	private static CSSRule[] parseDeclarations(SimpleBlock value) {
		return new CSSParserImp().parseAListOfDeclarations(value.getValue());
	}
	
}
