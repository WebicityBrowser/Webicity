package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.ElementStack;
import com.github.webicitybrowser.spiderhtml.misc.ElementUtil;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLogic;
import com.github.webicitybrowser.spiderhtml.misc.StackLogic;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;
import com.github.webicitybrowser.spiderhtml.token.TagToken;
import com.github.webicitybrowser.spiderhtml.token.Token;

public class InTableBodyInsertionMode implements InsertionMode {

	private final InRowInsertionMode inRowInsertionMode;
	private final InTableInsertionMode inTableInsertionMode;

	public InTableBodyInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.inRowInsertionMode = initializer.getInsertionMode(InRowInsertionMode.class);
		this.inTableInsertionMode = initializer.getInsertionMode(InTableInsertionMode.class);
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
			inTableInsertionMode.emit(context, insertionContext, token);
		}
	}

	private boolean handleStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		// TODO
		switch (token.getName(insertionContext.getStringCache())) {
		case "tr":
			handleTrStartTag(context, insertionContext, token);
			break;
		case "th":
		case "td":
			handThTdStartTag(context, insertionContext, token);
			break;
		case "caption":
		case "col":
		case "colgroup":
		case "tbody":
		case "tfoot":
		case "thead":
			handleTableSectionSwitch(context, insertionContext, token);
			break;
		default:
			return false;
		}

		return true;
	}

	private void handleTrStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		clearStackBackToTableBodyContext(context, insertionContext);
		InsertionLogic.insertHTMLElement(insertionContext, token);
		context.setInsertionMode(inRowInsertionMode);
	}

	private void handThTdStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		context.recordError();
		clearStackBackToTableBodyContext(context, insertionContext);
		InsertionLogic.insertHTMLElement(insertionContext, new StartTagToken("tr"));
		context.setInsertionMode(inRowInsertionMode);
		context.emit(token);
	}
	
	private boolean handleEndTag(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
		// TODO
		switch (token.getName(insertionContext.getStringCache())) {
		case "tbody":
		case "tfoot":
		case "thead":
			handleTableSectionEndTag(context, insertionContext, token);
			break;
		case "table":
			handleTableSectionSwitch(context, insertionContext, token);
			break;
		default:
			return false;
		}

		return true;
	}

	private void handleTableSectionEndTag(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
		ElementStack stack = insertionContext.getOpenElementStack();
		String tokenName = token.getName(insertionContext.getStringCache());
		if (!StackLogic.hasElementInTableScope(stack, tokenName, Namespace.HTML_NAMESPACE)) {
			context.recordError();
			return;
		}

		clearStackBackToTableBodyContext(context, insertionContext);
		stack.pop();
		context.setInsertionMode(inTableInsertionMode);
	}

	private void handleTableSectionSwitch(SharedContext context, InsertionContext insertionContext, TagToken token) {
		ElementStack stack = insertionContext.getOpenElementStack();
		if (!(
			StackLogic.hasElementInTableScope(stack, "tbody", Namespace.HTML_NAMESPACE) ||
			StackLogic.hasElementInTableScope(stack, "thead", Namespace.HTML_NAMESPACE) ||
			StackLogic.hasElementInTableScope(stack, "tfoot", Namespace.HTML_NAMESPACE)
		)) {
			context.recordError();
			return;
		}

		clearStackBackToTableBodyContext(context, insertionContext);
		stack.pop();
		context.setInsertionMode(inTableInsertionMode);
		context.emit(token);
	}

	public static void clearStackBackToTableBodyContext(SharedContext context, InsertionContext insertionContext) {
		ElementStack stack = insertionContext.getOpenElementStack();
		while (!ElementUtil.isHTMLElementWithOneOfName(stack.peek(), "tbody", "tfoot", "thead", "template")) {
			stack.pop();
		};
	}

}
