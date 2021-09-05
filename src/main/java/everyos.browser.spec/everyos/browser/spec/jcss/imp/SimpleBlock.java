package everyos.browser.spec.jcss.imp;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.spec.jcss.parser.CSSToken;

public class SimpleBlock implements CSSToken {
	private List<CSSToken> rules = new ArrayList<>();
	
	public void setAssociatedToken(CSSToken token) {
		
	}

	public void append(CSSToken value) {
		rules.add(value);
	}
	
	public CSSToken[] getValue() {
		return rules.toArray(new CSSToken[rules.size()]);
	}
}
