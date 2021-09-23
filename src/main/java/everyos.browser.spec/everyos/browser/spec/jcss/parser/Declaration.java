package everyos.browser.spec.jcss.parser;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.spec.jcss.intf.CSSRule;

public class Declaration implements CSSRule {

	private final CSSToken name;
	private final List<CSSToken> value;
	
	private boolean important = false;

	public Declaration(CSSToken name) {
		this.name = name;
		this.value = new ArrayList<>(1);
	}

	public void append(CSSToken value) {
		this.value.add(value);
	}

	public List<CSSToken> getValueAsList() {
		return value;
	}

	public void setImportant(boolean b) {
		this.important = true;
	}
}
