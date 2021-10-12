package everyos.browser.spec.jcss.imp;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.spec.jcss.parser.CSSToken;

public class Function implements CSSToken {
	
	private final List<Object> body = new ArrayList<>();
	
	private String name;
	
	public void setName(String value) {
		this.name = value;
	}

	public void append(Object value) {
		this.body.add(value);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Object[] getBody() {
		return this.body.toArray();
	}
	
}
