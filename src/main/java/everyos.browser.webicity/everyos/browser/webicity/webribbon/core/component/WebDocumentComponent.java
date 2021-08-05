package everyos.browser.webicity.webribbon.core.component;

import java.util.List;

import everyos.browser.javadom.intf.Document;
import everyos.browser.jcss.imp.QualifiedRule;
import everyos.browser.jcss.intf.CSSRule;
import everyos.browser.jcss.intf.CSSStyleSheet;
import everyos.browser.jcss.parser.IdentToken;
import everyos.browser.webicity.renderer.html.HTMLRenderer;
import everyos.browser.webicity.webribbon.core.cssom.CSSOMNode;

public class WebDocumentComponent extends WebComponent {
	private Document document;
	private CSSOMNode rootCSSOMNode;

	public WebDocumentComponent(HTMLRenderer renderer, Document node) {
		super(renderer, node);
		this.document = node;
		
		computeCSSOM();
	}
	
	//TODO: Move to CSSOMUtil
	//TODO: Track whether attributes are from UA or Site
	
	private void computeCSSOM() {
		this.rootCSSOMNode = new CSSOMNode();
		
		List<CSSStyleSheet> stylesheets = document.getDocumentOrShadowRootCSSStyleSheets();
		for (CSSStyleSheet stylesheet: stylesheets) {
			mergeCSSOM(rootCSSOMNode, stylesheet);
		}
	}

	private void mergeCSSOM(CSSOMNode root, CSSStyleSheet stylesheet) {
		for (CSSRule rule: stylesheet.getCSSRules()) {
			if (rule instanceof QualifiedRule) {
				mergeCSSOM(root, (QualifiedRule) rule);
			}
			//TODO: Handle at-rule
		}
	}

	private void mergeCSSOM(CSSOMNode root, QualifiedRule rule) {
		System.out.println(rule);
		for (Object pr: rule.getPrelude()) {
			System.out.println(pr);
			if (pr instanceof IdentToken) {
				System.out.println(((IdentToken) pr).getValue());
			}
		}
	}
}
