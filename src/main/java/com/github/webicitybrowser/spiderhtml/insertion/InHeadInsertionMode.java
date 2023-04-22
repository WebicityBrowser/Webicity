package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLogic;
import com.github.webicitybrowser.spiderhtml.misc.InsertionModeLogic;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;
import com.github.webicitybrowser.spiderhtml.token.Token;

public class InHeadInsertionMode implements InsertionMode {

	private final ParsingInitializer initializer;
	private final AfterHeadInsertionMode afterHeadInsertionMode;

	public InHeadInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.initializer = initializer;
		this.afterHeadInsertionMode = initializer.getInsertionMode(AfterHeadInsertionMode.class);
	}
	
	@Override
	public void emit(SharedContext context, InsertionContext insertionContext, Token token) {
		// TODO
		if (token instanceof CharacterToken characterToken) {
			int ch = characterToken.getCharacter();
			InsertionLogic.insertCharacters(context, insertionContext, new int [] { ch });
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
		switch (token.getName()) {
		case "noframes":
		case "style":
			handleRawTextStartTag(context, insertionContext, token);
			return true;
		default:
			return false;
		}
	}
	
	private void handleRawTextStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		InsertionModeLogic.followGenericRawTextParsingAlgorithm(initializer, context, insertionContext, token);
	}

	private boolean handleEndTag(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
		// TODO
		switch (token.getName()) {
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
