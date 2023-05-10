package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.ElementStack;
import com.github.webicitybrowser.spiderhtml.misc.ElementUtil;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;
import com.github.webicitybrowser.spiderhtml.token.Token;

public class InTableInsertionMode implements InsertionMode {
	
	private final InBodyInsertionMode inBodyInsertionMode;

	public InTableInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.inBodyInsertionMode = initializer.getInsertionMode(InBodyInsertionMode.class);
	}

	@Override
	public void emit(SharedContext context, InsertionContext insertionContext, Token token) {
		if (
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
			// TODO: Foster parenting
			inBodyInsertionMode.emit(context, insertionContext, token);
		}
	}
	
	private boolean handleStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		// TODO
		switch (token.getName(insertionContext.getStringCache())) {
		default:
			return false;
		}
	}
	
	private boolean handleEndTag(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
		// TODO
		switch (token.getName(insertionContext.getStringCache())) {
		case "table":
			// TODO: Ensure in scope, reset insertion mode properly
			ElementStack stack = insertionContext.getOpenElementStack();
			while (!ElementUtil.isHTMLElementWithName(stack.pop(), "table"));
			context.setInsertionMode(inBodyInsertionMode);
			return true;
		default:
			return false;
		}
	}
	
	// TODO: Write table logic tests. Handle col and colgroup.

}
