package everyos.browser.jcss.imp;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.jcss.parser.CSSToken;

public class SimpleBlock {
	private List<Object> rules = new ArrayList<>();
	
	public void setAssociatedToken(CSSToken token) {
		
	}

	public void append(Object value) {
		rules.add(value);
	}
	
	public Object[] getRules() {
		return rules.toArray();
	}
}
