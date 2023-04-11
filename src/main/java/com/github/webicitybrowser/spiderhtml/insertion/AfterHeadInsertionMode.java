package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLogic;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;
import com.github.webicitybrowser.spiderhtml.token.Token;

public class AfterHeadInsertionMode implements InsertionMode {

	private final InBodyInsertionMode inBodyInsertionMode;

	public AfterHeadInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.inBodyInsertionMode = initializer.getInsertionMode(InBodyInsertionMode.class);
	}
	
	@Override
	public void emit(SharedContext context, InsertionContext insertionContext, Token token) {
		// TODO
		InsertionLogic.insertHTMLElement(insertionContext, new StartTagToken("body"));
		context.setInsertionMode(inBodyInsertionMode);
		context.emit(token);
	}

}
