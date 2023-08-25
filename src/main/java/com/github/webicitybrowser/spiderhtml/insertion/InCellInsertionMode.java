package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.node.HTMLElement;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.ElementStack;
import com.github.webicitybrowser.spiderhtml.misc.StackLogic;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;
import com.github.webicitybrowser.spiderhtml.token.Token;

public class InCellInsertionMode implements InsertionMode {

	private final InRowInsertionMode inRowInsertionMode;
	private final InBodyInsertionMode inBodyInsertionMode;

	public InCellInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.inRowInsertionMode = initializer.getInsertionMode(InRowInsertionMode.class);
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
			inBodyInsertionMode.emit(context, insertionContext, token);
		}
	}

	private boolean handleStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		// TODO
		switch (token.getName(insertionContext.getStringCache())) {
		case "caption":
		case "col":
		case "colgroup":
		case "tbody":
		case "td":
		case "tfoot":
		case "th":
		case "thead":
		case "tr":
			handleTablePartStartTag(context, insertionContext, token);
			break;
		default:
			return false;
		}

		return true;
	}

	private void handleTablePartStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		ElementStack stack = insertionContext.getOpenElementStack();
		if (!(
			StackLogic.hasElementInTableScope(stack, "td", Namespace.HTML_NAMESPACE) ||
			StackLogic.hasElementInTableScope(stack, "th", Namespace.HTML_NAMESPACE)
		)) {
			context.recordError();
			return;
		}

		closeCell(context, insertionContext);
		context.emit(token);
	}

	private boolean handleEndTag(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
		// TODO
		switch (token.getName(insertionContext.getStringCache())) {
		case "td":
		case "th":
			handleThTdEndTag(context, insertionContext, token);
			break;
		case "table":
		case "tbody":
		case "tfoot":
		case "thead":
		case "tr":
			handleTableSectionEndTag(context, insertionContext, token);
			break;
		default:
			return false;
		}

		return true;
	}

	private void handleThTdEndTag(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
		String tokenName = token.getName(insertionContext.getStringCache());
		ElementStack stack = insertionContext.getOpenElementStack();
		if (!StackLogic.hasElementInTableScope(stack, tokenName, Namespace.HTML_NAMESPACE)) {
			context.recordError();
			return;
		}

		StackLogic.generateImpliedEndTags(stack, tokenName);
		if (!(stack.peek() instanceof HTMLElement element && element.getLocalName().equals(tokenName))) {
			context.recordError();
		}

		StackLogic.popUntil(stack, Namespace.HTML_NAMESPACE, tokenName);

		// TODO: clear active formatting elements

		context.setInsertionMode(inRowInsertionMode);
	}

	private void handleTableSectionEndTag(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
		String tokenName = token.getName(insertionContext.getStringCache());
		ElementStack stack = insertionContext.getOpenElementStack();
		if (!StackLogic.hasElementInTableScope(stack, tokenName, Namespace.HTML_NAMESPACE)) {
			context.recordError();
			return;
		}

		closeCell(context, insertionContext);
		context.emit(token);
	}

	private void closeCell(SharedContext context, InsertionContext insertionContext) {
		ElementStack stack = insertionContext.getOpenElementStack();
		StackLogic.generateImpliedEndTags(stack);
		if (!(
			stack.peek() instanceof HTMLElement element &&
			(element.getLocalName().equals("td") ||
			element.getLocalName().equals("th") ))
		) {
			context.recordError();
		}
		
		while (!(stack.pop() instanceof HTMLElement element && (
			element.getLocalName().equals("td") ||
			element.getLocalName().equals("th") ))
		);

		context.setInsertionMode(inRowInsertionMode);
	}

}
