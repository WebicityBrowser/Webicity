package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.tree.HTMLElementLeaf;
import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLogic;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;
import com.github.webicitybrowser.spiderhtml.token.Token;

public class BeforeHeadInsertionMode implements InsertionMode {
	
	private final InHeadInsertionMode inHeadInsertionMode;

	public BeforeHeadInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.inHeadInsertionMode = initializer.getInsertionMode(InHeadInsertionMode.class);
	}

	@Override
	public void emit(SharedContext context, InsertionContext insertionContext, Token token) {
		// TODO
		HTMLElementLeaf headLeaf = InsertionLogic.insertHTMLElement(insertionContext, new StartTagToken("head"));
		insertionContext.setHeadElementPointer(headLeaf);
		context.setInsertionMode(inHeadInsertionMode);
		context.emit(token);
	}

}
