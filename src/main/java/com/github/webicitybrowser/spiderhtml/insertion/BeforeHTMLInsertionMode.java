package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.tree.HTMLDocumentLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLElementLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLHtmlElementLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLTreeBuilder;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLogic;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;
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
		if (
			token instanceof StartTagToken startTagToken &&
			handleStartTagToken(context, insertionContext, startTagToken)
		) {
			return;
		} else {
			pushHtmlLeafToStack(insertionContext);
			context.setInsertionMode(beforeHeadInsertionMode);
			context.emit(token);
		}
	}

	private void pushHtmlLeafToStack(InsertionContext insertionContext) {
		HTMLTreeBuilder treeBuilder = insertionContext.getTreeBuilder();
		HTMLDocumentLeaf nodeDocument = treeBuilder.getDocument();
		HTMLHtmlElementLeaf htmlLeaf = treeBuilder.createHtmlElementLeaf(nodeDocument);
		nodeDocument.appendLeaf(htmlLeaf);
		insertionContext.getOpenElementStack().push(htmlLeaf);
	}
	
	private boolean handleStartTagToken(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		switch (token.getName()) {
		case "html":
			handleHtmlStartTag(context, insertionContext, token);
			return true;
		default:
			return false;
		}
	}

	private void handleHtmlStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		HTMLTreeBuilder treeBuilder = insertionContext.getTreeBuilder();
		HTMLElementLeaf htmlElement = InsertionLogic.createElementForToken(
			insertionContext, token, Namespace.HTML_NAMESPACE, treeBuilder.getDocument());
		treeBuilder.getDocument().appendLeaf(htmlElement);
		insertionContext.getOpenElementStack().push(htmlElement);
		
		context.setInsertionMode(beforeHeadInsertionMode);
	}

}
