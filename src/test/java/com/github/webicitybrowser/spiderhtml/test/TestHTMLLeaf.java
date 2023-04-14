package com.github.webicitybrowser.spiderhtml.test;

import java.util.LinkedList;
import java.util.List;

import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLLeaf;

public class TestHTMLLeaf implements HTMLLeaf {

	private final TestHTMLDocumentLeaf nodeDocument;
	private final List<TestHTMLLeaf> children = new LinkedList<>();
	
	public TestHTMLLeaf(TestHTMLDocumentLeaf nodeDocument) {
		this.nodeDocument = nodeDocument;
	}
	
	@Override
	public void appendLeaf(HTMLLeaf htmlLeaf) {
		children.add((TestHTMLLeaf) htmlLeaf);
	}
	
	@Override
	public void insertBeforeLeaf(HTMLLeaf node, HTMLLeaf child) {
		if (node == null) {
			children.add((TestHTMLLeaf) child);
		} else {
			int pos = children.indexOf(node);
			assert pos != -1;
			children.add(pos, (TestHTMLLeaf) child);
		}
	}

	@Override
	public int getLength() {
		return children.size();
	}
	
	@Override
	public HTMLDocumentLeaf getNodeDocument() {
		return this.nodeDocument;
	}
	
	public List<TestHTMLLeaf> getChildren() {
		return this.children;
	}
	
}
