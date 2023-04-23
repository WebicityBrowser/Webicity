package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.node.HTMLElement;
import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLogic;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.CommentToken;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;
import com.github.webicitybrowser.spiderhtml.token.Token;
import com.github.webicitybrowser.spiderhtml.util.ASCIIUtil;

public class BeforeHeadInsertionMode implements InsertionMode {
	
	private final InHeadInsertionMode inHeadInsertionMode;

	public BeforeHeadInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.inHeadInsertionMode = initializer.getInsertionMode(InHeadInsertionMode.class);
	}

	@Override
	public void emit(SharedContext context, InsertionContext insertionContext, Token token) {
		if (token instanceof CharacterToken characterToken) {
			handleCharacterToken(characterToken);
		} else if (token instanceof CommentToken commentToken) {
			InsertionLogic.insertComment(insertionContext, commentToken, null);
		} else if (
			token instanceof StartTagToken startTagToken &&
			handleStartTagToken(context, insertionContext, startTagToken)
		) {
			return;
		} else {
			HTMLElement headElement = InsertionLogic.insertHTMLElement(insertionContext, new StartTagToken("head"));
			insertionContext.setHeadElementPointer(headElement);
			context.setInsertionMode(inHeadInsertionMode);
			context.emit(token);
		}
	}
	
	private void handleCharacterToken(CharacterToken characterToken) {
		if (ASCIIUtil.isASCIIWhiteSpace(characterToken.getCharacter())) {
			return;
		} else {
			throw new UnsupportedOperationException();
		}
	}

	private boolean handleStartTagToken(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		switch (token.getName()) {
		case "head":
			handleHeadStartTag(context, insertionContext, token);
			return true;
		default:
			return false;
		}
	}

	private void handleHeadStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		HTMLElement headElement = InsertionLogic.insertHTMLElement(insertionContext, token);
		insertionContext.setHeadElementPointer(headElement);
		
		context.setInsertionMode(inHeadInsertionMode);
	}
	
}
