package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;
import com.github.webicitybrowser.spiderhtml.token.Token;
import com.github.webicitybrowser.spiderhtml.util.ASCIIUtil;

public class AfterBodyInsertionMode implements InsertionMode {

	private final InBodyInsertionMode inBodyInsertionMode;
	private final AfterAfterBodyInsertionMode afterAfterBodyInsertionMode;

	public AfterBodyInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.inBodyInsertionMode = initializer.getInsertionMode(InBodyInsertionMode.class);
		this.afterAfterBodyInsertionMode = initializer.getInsertionMode(AfterAfterBodyInsertionMode.class);
	}
	
	@Override
	public void emit(SharedContext context, InsertionContext insertionContext, Token token) {
		// TODO
		if (
			token instanceof CharacterToken characterToken &&
			ASCIIUtil.isASCIIWhiteSpace(characterToken.getCharacter())
		) {
			inBodyInsertionMode.emit(context, insertionContext, characterToken);
		} else if (
			token instanceof EndTagToken endToken &&
			handleEndTagToken(context, insertionContext, endToken)
		) {
			return;
		} else {
			throw new UnsupportedOperationException();
		}
	}

	private boolean handleEndTagToken(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
		switch (token.getName()) {
		case "html":
			handleHtmlEndTag(context, insertionContext, token);
			return true;
		default:
			return false;
		}
	}

	private void handleHtmlEndTag(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
		// TODO
		context.setInsertionMode(afterAfterBodyInsertionMode);
	}

}
