package everyos.browser.spec.javadom.imp;

import java.util.Iterator;
import java.util.List;

import everyos.browser.spec.javadom.intf.DOMTokenList;

public class JDDOMTokenList implements DOMTokenList {
	
	private List<String> list;
	
	public JDDOMTokenList(List<String> list) {
		this.list = list;
	}

	@Override
	public int getLength() {
		return list.size();
	}

	@Override
	public boolean contains(String token) {
		return this.list.contains(token);
	}

	@Override
	public Iterator<String> iterator() {
		return list.iterator();
	}
	
}
