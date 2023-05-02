package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.html.node.HTMLElement;
import com.github.webicitybrowser.spec.html.node.HTMLHtmlElement;
import com.github.webicitybrowser.spec.html.parse.HTMLTreeBuilder;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLocation;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLogic;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.CommentToken;
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
		if (token instanceof CharacterToken characterToken) {
			handleCharacterToken(characterToken);
		} else if (token instanceof CommentToken commentToken) {
			handleCommentToken(insertionContext, commentToken);
		} else if (
			token instanceof StartTagToken startTagToken &&
			handleStartTagToken(context, insertionContext, startTagToken)
		) {
			return;
		} else {
			pushHtmlNodeToStack(insertionContext);
			context.setInsertionMode(beforeHeadInsertionMode);
			context.emit(token);
		}
	}

	private void pushHtmlNodeToStack(InsertionContext insertionContext) {
		HTMLTreeBuilder treeBuilder = insertionContext.getTreeBuilder();
		Document nodeDocument = treeBuilder.getDocument();
		HTMLHtmlElement htmlElementNode = treeBuilder.createHtmlElement(nodeDocument);
		nodeDocument.appendChild(htmlElementNode);
		insertionContext.getOpenElementStack().push(htmlElementNode);
	}
	
	private void handleCharacterToken(CharacterToken characterToken) {
		if (ASCIIUtil.isASCIIWhiteSpace(characterToken.getCharacter())) {
			return;
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	private void handleCommentToken(InsertionContext insertionContext, CommentToken commentToken) {
		Node htmlNode = insertionContext.getOpenElementStack().peek();
		InsertionLocation position = new InsertionLocation(htmlNode, null);
		InsertionLogic.insertComment(insertionContext, commentToken, position);
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
		HTMLElement htmlElement = InsertionLogic.createElementForToken(
			insertionContext, token, Namespace.HTML_NAMESPACE, treeBuilder.getDocument());
		treeBuilder.getDocument().appendChild(htmlElement);
		insertionContext.getOpenElementStack().push(htmlElement);
		
		context.setInsertionMode(beforeHeadInsertionMode);
	}

}
