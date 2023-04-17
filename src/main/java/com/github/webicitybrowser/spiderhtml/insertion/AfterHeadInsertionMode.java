package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLogic;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
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
		if (token instanceof CharacterToken characterToken) {
			int ch = characterToken.getCharacter();
			InsertionLogic.insertCharacters(context, insertionContext, new int [] { ch });
		} else if (
			token instanceof StartTagToken startTagToken &&
			handleStartTagToken(context, insertionContext, startTagToken)
		) {
			return;
		} else {
			InsertionLogic.insertHTMLElement(insertionContext, new StartTagToken("body"));
			context.setInsertionMode(inBodyInsertionMode);
			context.emit(token);
		}
	}
	
	private boolean handleStartTagToken(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		switch (token.getName()) {
		case "body":
			handleBodyStartTag(context, insertionContext, token);
			return true;
		default:
			return false;
		}
	}

	private void handleBodyStartTag(SharedContext context, InsertionContext insertionContext, StartTagToken token) {
		InsertionLogic.insertHTMLElement(insertionContext, token);
		// TODO: Set frameset-ok
		context.setInsertionMode(inBodyInsertionMode);
	}

}
