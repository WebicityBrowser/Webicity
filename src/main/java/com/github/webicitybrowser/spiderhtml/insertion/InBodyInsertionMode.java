package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.Token;

public class InBodyInsertionMode implements InsertionMode {
	
	public InBodyInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
	}

	@Override
	public void emit(SharedContext context, InsertionContext insertionContext, Token token) {
		// TODO
		insertionContext.stopParsing();
	}

}
