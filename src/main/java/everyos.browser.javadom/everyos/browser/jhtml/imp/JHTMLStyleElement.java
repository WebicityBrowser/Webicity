package everyos.browser.jhtml.imp;

import everyos.browser.jcss.intf.CSSStyleSheet;
import everyos.browser.jhtml.intf.HTMLStyleElement;
import everyos.browser.jhtml.parser.ElementFactory;

public class JHTMLStyleElement extends JHTMLElement implements HTMLStyleElement {
	private CSSStyleSheet sheet;
	
	public JHTMLStyleElement(ElementFactory factory) {
		super(factory);
	}

	@Override
	public CSSStyleSheet getSheet() {
		return getAssociatedCSSStyleSheet();
	}

	//TODO: This isn't an IDL method, so should we still have it?
	@Override
	public void update() {
		removeTheCSSStyleSheetInQuestion();
		//TODO: Check if we are connected
		String type = getAttribute("type");
		if (type!=null && !(type.isEmpty()||type.equals("text/css"))) return;
		//TODO: CSP policy
		
	}

	private void removeTheCSSStyleSheetInQuestion() {
		// TODO
		sheet = null;
	}
	
	private CSSStyleSheet getAssociatedCSSStyleSheet() {
		return sheet;
	}
}
