package com.github.webicitybrowser.spiderhtml.context;

import com.github.webicitybrowser.spec.html.node.HTMLElement;
import com.github.webicitybrowser.spec.html.parse.HTMLTreeBuilder;
import com.github.webicitybrowser.spiderhtml.insertion.InsertionMode;
import com.github.webicitybrowser.spiderhtml.misc.ElementStack;

public class InsertionContext {

	private final SharedContext sharedContext;
	private final HTMLTreeBuilder treeBuilder;
	private final ElementStack stack = new ElementStack();
	
	private InsertionMode originalInsertionMode;
	
	@SuppressWarnings("unused")
	private HTMLElement headNode;
	
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

	public void setHeadElementPointer(HTMLElement headNode) {
		this.headNode = headNode;
	}

	public void setOriginalInsertionMode(InsertionMode originalInsertionMode) {
		this.originalInsertionMode = originalInsertionMode;
	}
	
	public InsertionMode getOriginalInsertionMode() {
		return this.originalInsertionMode;
	}

}
