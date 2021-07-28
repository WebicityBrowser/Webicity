package everyos.browser.jhtml.imp;

import everyos.browser.jcss.imp.JCSSStyleSheet;
import everyos.browser.jcss.intf.CSSStyleSheet;
import everyos.browser.jcss.parser.JCSSParser;
import everyos.browser.jcss.parser.JCSSTokenizer;
import everyos.browser.jhtml.intf.HTMLStyleElement;
import everyos.browser.jhtml.parser.ElementFactory;

public class JHTMLStyleElement extends JHTMLElement implements HTMLStyleElement {
	private JCSSStyleSheet sheet;
	
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
		CSSStyleSheet stylesheet = JCSSStyleSheet.create(getOwnerDocument());
		//TODO: Move this to .create
		stylesheet.setCSSRules(new JCSSParser().parseAListOfRules(new JCSSTokenizer().createFromString(this.getChildTextContent())));
	}

	private void removeTheCSSStyleSheetInQuestion() {
		// TODO
		sheet = null;
	}
	
	private JCSSStyleSheet getAssociatedCSSStyleSheet() {
		return sheet;
	}
}
