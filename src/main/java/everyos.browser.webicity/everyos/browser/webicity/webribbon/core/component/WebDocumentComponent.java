package everyos.browser.webicity.webribbon.core.component;

import java.util.List;

import everyos.browser.javadom.intf.Document;
import everyos.browser.jcss.cssom.CSSOMNode;
import everyos.browser.jcss.cssom.CSSOMUtil;
import everyos.browser.jcss.intf.CSSStyleSheet;
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
