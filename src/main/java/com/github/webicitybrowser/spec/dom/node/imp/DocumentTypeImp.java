package com.github.webicitybrowser.spec.dom.node.imp;

import com.github.webicitybrowser.spec.dom.node.DocumentType;

public class DocumentTypeImp extends NodeImp implements DocumentType {

	private final String name;

	public DocumentTypeImp(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return "<!doctype " + name + ">";
	}
	
}
