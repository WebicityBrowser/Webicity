package com.github.webicitybrowser.spec.dom.node.imp;

import com.github.webicitybrowser.spec.dom.node.Comment;
import com.github.webicitybrowser.spec.dom.node.Document;

public class CommentImp extends CharacterDataImp implements Comment {

	public CommentImp(Document nodeDocument, String data) {
		super(nodeDocument, data);
	}

}
