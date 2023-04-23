package com.github.webicitybrowser.spec.dom.node.imp;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.Text;

public class TextImp extends CharacterDataImp implements Text {

	public TextImp(Document document) {
		super(document, "");
	}
	
}
