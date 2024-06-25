package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.node.HTMLElement;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLocation;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLogic;
import com.github.webicitybrowser.spiderhtml.misc.InsertionModeLogic;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.CommentToken;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;
import com.github.webicitybrowser.spiderhtml.token.Token;
import com.github.webicitybrowser.spiderhtml.tokenize.ScriptDataState;

public class InHeadInsertionMode implements InsertionMode {

	private final ParsingInitializer initializer;
	private final AfterHeadInsertionMode afterHeadInsertionMode;
	private final TextInsertionMode textInsertionMode;
	private final ScriptDataState scriptDataState;

	public InHeadInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.initializer = initializer;
		this.afterHeadInsertionMode = initializer.getInsertionMode(AfterHeadInsertionMode.class);
		this.textInsertionMode = initializer.getInsertionMode(TextInsertionMode.class);
		this.scriptDataState = initializer.getTokenizeState(ScriptDataState.class);
	}
	
	@Override
	public void emit(SharedContext context, InsertionContext insertionContext, Token token) {
		// TODO
		if (token instanceof CharacterToken characterToken) {
			int ch = characterToken.getCharacter();
			InsertionLogic.insertCharacters(context, insertionContext, new int [] { ch });
		} else if (token instanceof CommentToken commentToken) {
			InsertionLogic.insertComment(insertionContext, commentToken, null);
		} else if (
			token instanceof StartTagToken startTagToken &&
			handleStartTag(context, insertionContext, startTagToken)
		) {
			return;
		} else if (
			token instanceof EndTagToken endTagToken &&
			handleEndTag(context, insertionContext, endTagToken)
		) {
			return;
		} else {
			insertionContext.getOpenElementStack().pop();
			context.setInsertionMode(afterHeadInsertionMode);
			context.emit(token);
		}
	}
	
	private boolean handleStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		// TODO
		switch (token.getName(insertionContext.getStringCache())) {
		case "base":
		case "basefont":
		case "bgsound":
		case "link":
			handleSelfClosingStartTag(context, insertionContext, token);
			return true;
		case "meta":
			handleMetaStartTag(context, insertionContext, token);
			return true;
		case "title":
			handleRCDataStartTag(context, insertionContext, token);
			return true;
		case "noframes":
		case "style":
			handleRawTextStartTag(context, insertionContext, token);
			return true;
		case "script":
			handleScriptStartTag(context, insertionContext, token);
			return true;
		default:
			return false;
		}
	}

	private void handleSelfClosingStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		InsertionLogic.insertHTMLElement(insertionContext, token);
		insertionContext.getOpenElementStack().pop();
		if (token.isSelfClosingTag()) {
			token.acknowledgeSelfClosingTag();
		}
	}

	private void handleMetaStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		InsertionLogic.insertHTMLElement(insertionContext, token);
		insertionContext.getOpenElementStack().pop();
		if (token.isSelfClosingTag()) {
			token.acknowledgeSelfClosingTag();
		}
		// TODO: Speculative HTML Parser
	}

	private void handleRCDataStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		InsertionModeLogic.followGenericRCDataElementParsingAlgorithm(initializer, context, insertionContext, token);
	}
	
	private void handleRawTextStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		InsertionModeLogic.followGenericRawTextElementParsingAlgorithm(initializer, context, insertionContext, token);
	}
	
	private void handleScriptStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		InsertionLocation adjustedInsertionLocation = InsertionLogic.getAppropriatePlaceForInsertingNode(insertionContext, null);
		HTMLElement scriptElement = InsertionLogic.createElementForToken(
			insertionContext, token, Namespace.HTML_NAMESPACE, adjustedInsertionLocation.parent());
		// TODO: Implement silly things
		InsertionLogic.insertNode(adjustedInsertionLocation, scriptElement);
		insertionContext.getOpenElementStack().push(scriptElement);
		context.setTokenizeState(scriptDataState);
		insertionContext.setOriginalInsertionMode(context.getInsertionMode());
		context.setInsertionMode(textInsertionMode);
	}

	private boolean handleEndTag(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
		// TODO
		switch (token.getName(insertionContext.getStringCache())) {
		case "head":
			handleHeadEndTag(context, insertionContext, token);
			return true;
		default:
			return false;
		}
	}

	private void handleHeadEndTag(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
		insertionContext.getOpenElementStack().pop();
		context.setInsertionMode(afterHeadInsertionMode);
	}
	
}
