package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.ElementStack;
import com.github.webicitybrowser.spiderhtml.misc.ElementUtil;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLogic;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;
import com.github.webicitybrowser.spiderhtml.token.Token;

public class InTableInsertionMode implements InsertionMode {
	
	private final InBodyInsertionMode inBodyInsertionMode;
	private final InTableBodyInsertionMode inTableBodyInsertionMode;

	public InTableInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.inBodyInsertionMode = initializer.getInsertionMode(InBodyInsertionMode.class);
		this.inTableBodyInsertionMode = initializer.getInsertionMode(InTableBodyInsertionMode.class);
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
		case "tbody":
		case "tfoot":
		case "thead":
			handleTableSectionStartTag(context, insertionContext, token);
			break;
		case "td":
		case "th":
		case "tr":
			handleTableEntryStartTag(context, insertionContext, token);
			break;
		default:
			return false;
		}

		return true;
	}
	
	private void handleTableSectionStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		clearStackBackToTableContext(context, insertionContext);
		InsertionLogic.insertHTMLElement(insertionContext, token);
		context.setInsertionMode(inTableBodyInsertionMode);
	}

	private void handleTableEntryStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		clearStackBackToTableContext(context, insertionContext);
		InsertionLogic.insertHTMLElement(insertionContext, new StartTagToken("tbody"));
		context.setInsertionMode(inTableBodyInsertionMode);
		context.emit(token);
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

	public static void clearStackBackToTableContext(SharedContext context, InsertionContext insertionContext) {
		ElementStack stack = insertionContext.getOpenElementStack();
		while (!ElementUtil.isHTMLElementWithOneOfName(stack.peek(), "table", "template", "html")) {
			stack.pop();
		};
	}

	// TODO: Write table logic tests. Handle col and colgroup.

}
