package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.html.node.HTMLElement;
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

public class InRowInsertionMode implements InsertionMode {

	private final InsertionMode inCellInsertionMode;
	private final InsertionMode inTableBodyInsertionMode;
	private final InsertionMode inTableInsertionMode;

	public InRowInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.inCellInsertionMode = initializer.getInsertionMode(InCellInsertionMode.class);
		this.inTableBodyInsertionMode = initializer.getInsertionMode(InTableBodyInsertionMode.class);
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
		case "th":
		case "td":
			handleThTdStartTag(context, insertionContext, token);
			break;
		case "caption":
		case "col":
		case "colgroup":
		case "tbody":
		case "tfoot":
		case "thead":
		case "tr":
			handleTablePartSwitch(context, insertionContext, token);
			break;
		default:
			return false;
		}

		return true;
	}

	private void handleThTdStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		clearStackBackToTableRowContext(context, insertionContext);
		InsertionLogic.insertHTMLElement(insertionContext, token);
		context.setInsertionMode(inCellInsertionMode);
		// TODO: insert marker
	}

	private boolean handleEndTag(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
		// TODO
		switch (token.getName(insertionContext.getStringCache())) {
		case "tr":
			handleTrEndTag(context, insertionContext, token);
			break;
		case "table":
			handleTablePartSwitch(context, insertionContext, token);
			break;
		default:
			return false;
		}

		return true;
	}

	private void handleTrEndTag(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
		ElementStack stack = insertionContext.getOpenElementStack();
		if (!StackLogic.hasElementInTableScope(stack, "tr", Namespace.HTML_NAMESPACE)) {
			context.recordError();
			return;
		}
		clearStackBackToTableRowContext(context, insertionContext);
		stack.pop();
		context.setInsertionMode(inTableBodyInsertionMode);
	}

	private void handleTablePartSwitch(SharedContext context, InsertionContext insertionContext, TagToken token) {
		ElementStack stack = insertionContext.getOpenElementStack();
		if (!StackLogic.hasElementInTableScope(stack, "tr", Namespace.HTML_NAMESPACE)) {
			context.recordError();
			return;
		}
		clearStackBackToTableRowContext(context, insertionContext);
		Node poppedNode = stack.pop();
		assert poppedNode instanceof HTMLElement element && element.getLocalName().equals("tr");
		context.setInsertionMode(inTableBodyInsertionMode);
		context.emit(token);
	}

	private void clearStackBackToTableRowContext(SharedContext context, InsertionContext insertionContext) {
		ElementStack stack = insertionContext.getOpenElementStack();
		while (!ElementUtil.isHTMLElementWithOneOfName(stack.peek(), "tr", "template", "html")) {
			stack.pop();
		};
	}

}
