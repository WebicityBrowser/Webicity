package everyos.browser.spec.jcss.imp;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.spec.jcss.intf.CSSRule;

public class QualifiedRule implements CSSRule {
	List<Object> prelude = new ArrayList<>();
	private Object block;
	
	public void appendToPrelude(Object value) {
		prelude.add(value);
	}

	public void setBlock(Object block) {
		this.block = block;
	}
	
	public Object[] getPrelude() {
		return prelude.toArray();
	}
	
	public Object getBlock() {
		return block;
	}
}
