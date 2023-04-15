package com.github.webicitybrowser.spec.dom.node.imp;

import com.github.webicitybrowser.spec.dom.node.Text;

public class TextImp extends NodeImp implements Text {
	
	private final StringBuilder data = new StringBuilder();

	@Override
	public void appendData(String string) {
		data.append(string);
	}
	
	@Override
	public String getData() {
		return data.toString();
	}

}
