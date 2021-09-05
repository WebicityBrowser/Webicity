package everyos.browser.webicity.webribbon.core.component;

import java.util.List;

import everyos.browser.spec.javadom.intf.Document;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.spec.jcss.cssom.CSSOMUtil;
import everyos.browser.spec.jcss.intf.CSSStyleSheet;
import everyos.browser.webicity.renderer.html.HTMLRenderer;

public class WebDocumentComponent extends WebComponent {
	private Document document;

	public WebDocumentComponent(HTMLRenderer renderer, Document node) {
		super(renderer, node);
		this.document = node;
		
		//TODO: This is likely not where this goes
		computeCSSOM();
	}

	private CSSOMNode computeCSSOM() {
		List<CSSStyleSheet> stylesheets = document.getDocumentOrShadowRootCSSStyleSheets();
		
		return CSSOMUtil.computeCSSOM(stylesheets.toArray(new CSSStyleSheet[stylesheets.size()]));
	}
	
	
}
