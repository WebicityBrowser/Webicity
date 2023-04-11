package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
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
		insertionContext.getOpenElementStack().pop();
		context.setInsertionMode(afterHeadInsertionMode);
		context.emit(token);
	}

}
