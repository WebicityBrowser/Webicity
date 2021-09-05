package everyos.browser.spec.jcss.parser;

import everyos.browser.spec.jcss.intf.CSSRule;

public class Declaration implements CSSRule {

	private CSSToken name;

	public Declaration(CSSToken name) {
		this.name = name;
	}

}
