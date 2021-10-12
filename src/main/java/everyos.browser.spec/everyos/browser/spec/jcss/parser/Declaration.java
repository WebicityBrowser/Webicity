package everyos.browser.spec.jcss.parser;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.spec.jcss.intf.CSSRule;

public class Declaration implements CSSRule {

	private final IdentToken name;
	private final List<CSSToken> value;
	
	private boolean important = false;

	public Declaration(IdentToken name) {
		this.name = name;
		this.value = new ArrayList<>(1);
	}
	
	public String getName() {
		return this.name.getValue();
	}

	public void append(CSSToken value) {
		this.value.add(value);
	}

	public List<CSSToken> getValueAsList() {
		return value;
	}
	
	public CSSToken[] getValue() {
		return value.toArray(new CSSToken[value.size()]);
	}

	public void setImportant(boolean b) {
		this.important = true;
	}
	
	public boolean getImportant() {
		return this.important;
	}
	
	//TODO: Track origin
}
