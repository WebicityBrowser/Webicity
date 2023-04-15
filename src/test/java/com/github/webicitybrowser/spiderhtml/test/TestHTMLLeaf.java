package com.github.webicitybrowser.spiderhtml.test;

import java.util.LinkedList;
import java.util.List;

import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLLeaf;

public class TestHTMLLeaf implements HTMLLeaf {

	private final TestHTMLDocumentLeaf nodeDocument;
	private final List<TestHTMLLeaf> children = new LinkedList<>();
	
	private TestHTMLLeaf parentLeaf;
	
	public TestHTMLLeaf(TestHTMLDocumentLeaf nodeDocument) {
		this.nodeDocument = nodeDocument;
	}
	
	@Override
	public HTMLLeaf getPreviousSibling() {
		if (parentLeaf == null) {
			return null;
		}
		TestHTMLLeaf previousLeaf = null;
		for (TestHTMLLeaf child: parentLeaf.getChildren()) {
			if (child == this) {
				return previousLeaf;
			}
			previousLeaf = child;
		}
		return null;
	}
	
	@Override
	public HTMLLeaf getLastChild() {
		if (children.isEmpty()) {
			return null;
		} else {
			return children.get(getLength() - 1);
		}
	}
	
	@Override
	public void appendLeaf(HTMLLeaf htmlLeaf) {
		children.add((TestHTMLLeaf) htmlLeaf);
		((TestHTMLLeaf) htmlLeaf).setParent(this);
	}
	
	@Override
	public void insertBeforeLeaf(HTMLLeaf node, HTMLLeaf child) {
		((TestHTMLLeaf) child).setParent(this);
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
	
	private void setParent(TestHTMLLeaf parent) {
		this.parentLeaf = parent;
	}
	
	public List<TestHTMLLeaf> getChildren() {
		return this.children;
	}
	
}
