package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;
import com.github.webicitybrowser.spiderhtml.token.Token;

public class InHeadInsertionMode implements InsertionMode {

	private final AfterHeadInsertionMode afterHeadInsertionMode;

	public InHeadInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.afterHeadInsertionMode = initializer.getInsertionMode(AfterHeadInsertionMode.class);
	}
	
	@Override
	public void emit(SharedContext context, InsertionContext insertionContext, Token token) {
		// TODO
		if (
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
	
	private boolean handleEndTag(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
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
