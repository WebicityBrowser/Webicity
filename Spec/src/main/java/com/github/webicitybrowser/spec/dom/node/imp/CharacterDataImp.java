package com.github.webicitybrowser.spec.dom.node.imp;

import com.github.webicitybrowser.spec.dom.node.CharacterData;
import com.github.webicitybrowser.spec.dom.node.Document;

public class CharacterDataImp extends NodeImp implements CharacterData {

	private final StringBuilder data;
	
	public CharacterDataImp(Document nodeDocument, String data) {
		super(nodeDocument);
		this.data = new StringBuilder(data);
	}

	@Override
	public void appendData(String string) {
		data.append(string);
	}
	
	@Override
	public String getData() {
		return data.toString();
	}

	@Override
	public String toString() {
		return data.toString();
	}
	
}
