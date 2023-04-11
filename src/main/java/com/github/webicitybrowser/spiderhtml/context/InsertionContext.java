package com.github.webicitybrowser.spiderhtml.context;

import com.github.webicitybrowser.spec.html.parse.tree.HTMLElementLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLTreeBuilder;
import com.github.webicitybrowser.spiderhtml.misc.ElementStack;

public class InsertionContext {

	private final SharedContext sharedContext;
	private final HTMLTreeBuilder treeBuilder;
	private final ElementStack stack = new ElementStack();
	
	@SuppressWarnings("unused")
	private HTMLElementLeaf headLeaf;
	
	public InsertionContext(SharedContext sharedContext, HTMLTreeBuilder treeBuilder) {
		this.sharedContext = sharedContext;
		this.treeBuilder = treeBuilder;
	}

	public HTMLTreeBuilder getTreeBuilder() {
		return this.treeBuilder;
	}
	
	public void stopParsing() {
		sharedContext.setTokenizeState(null);
	}

	public ElementStack getOpenElementStack() {
		return this.stack;
	}

	public void setHeadElementPointer(HTMLElementLeaf headLeaf) {
		this.headLeaf = headLeaf;
	}

}
