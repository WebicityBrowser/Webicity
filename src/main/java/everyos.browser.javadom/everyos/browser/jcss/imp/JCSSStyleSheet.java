package everyos.browser.jcss.imp;

import everyos.browser.javadom.intf.Document;
import everyos.browser.jcss.intf.CSSRule;
import everyos.browser.jcss.intf.CSSStyleSheet;
import everyos.browser.jcss.intf.MediaList;

public class JCSSStyleSheet implements CSSStyleSheet {
	public static JCSSStyleSheet create(Document document) {
		JCSSStyleSheet stylesheet = new JCSSStyleSheet(document, null);
		//TODO: Set properties
		addCSSStyleSheet(document, stylesheet);
		return stylesheet;
		
	}
	
	private static void addCSSStyleSheet(Document document, CSSStyleSheet stylesheet) {
		document.getDocumentOrShadowRootCSSStyleSheets().add(stylesheet);
	}

	private CSSRule[] rules;

	public JCSSStyleSheet(Document document, CSSStyleSheetInit options) {
		//TODO
	}

	@Override
	public String getType() {
		// TODO
		return null;
	}

	@Override
	public String getHref() {
		// TODO
		return null;
	}

	@Override
	public Object getOwnerNode() {
		// TODO
		return null;
	}

	@Override
	public CSSStyleSheet getParentStyleSheet() {
		// TODO
		return null;
	}

	@Override
	public String getTitle() {
		// TODO
		return null;
	}

	@Override
	public MediaList getMedia() {
		// TODO
		return null;
	}

	@Override
	public boolean getDisabled() {
		// TODO
		return false;
	}

	@Override
	public boolean setDisabled(boolean disabled) {
		// TODO
		return false;
	}

	
	//
	@Override
	public void setCSSRules(CSSRule[] rules) {
		this.rules = rules;
	}
	
	@Override
	public CSSRule[] getCSSRules() {
		return this.rules;
	}
}
