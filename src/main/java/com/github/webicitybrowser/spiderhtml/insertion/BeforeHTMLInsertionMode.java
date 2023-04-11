package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLHtmlElementLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLTreeBuilder;
import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.Token;

public class BeforeHTMLInsertionMode implements InsertionMode {

	private final BeforeHeadInsertionMode beforeHeadInsertionMode;

	public BeforeHTMLInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.beforeHeadInsertionMode = initializer.getInsertionMode(BeforeHeadInsertionMode.class);
	}
	
	@Override
	public void emit(SharedContext context, InsertionContext insertionContext, Token token) {
		// TODO
		pushHtmlLeafToStack(insertionContext);
		context.setInsertionMode(beforeHeadInsertionMode);
		context.emit(token);
	}

	private void pushHtmlLeafToStack(InsertionContext insertionContext) {
		HTMLTreeBuilder treeBuilder = insertionContext.getTreeBuilder();
		HTMLDocumentLeaf nodeDocument = treeBuilder.getDocument();
		HTMLHtmlElementLeaf htmlLeaf = treeBuilder.createHtmlElementLeaf(nodeDocument);
		nodeDocument.appendLeaf(htmlLeaf);
		insertionContext.getOpenElementStack().push(htmlLeaf);
	}

}
