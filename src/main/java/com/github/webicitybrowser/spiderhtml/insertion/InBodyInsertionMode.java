package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLogic;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;
import com.github.webicitybrowser.spiderhtml.token.Token;

public class InBodyInsertionMode implements InsertionMode {
	
	private final AfterBodyInsertionMode afterBodyInsertionMode;

	public InBodyInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.afterBodyInsertionMode = initializer.getInsertionMode(AfterBodyInsertionMode.class);
	}

	@Override
	public void emit(SharedContext context, InsertionContext insertionContext, Token token) {
		// TODO
		if (token instanceof CharacterToken characterToken) {
			handleCharacter(context, insertionContext, characterToken);
		} else if (
			token instanceof EndTagToken endTagToken &&
			handleEndTag(context, insertionContext, endTagToken)
		) {
			return;
		} else if (token instanceof EOFToken) {
			insertionContext.stopParsing();
		} else {
			throw new UnsupportedOperationException();
		}
	}

	private void handleCharacter(SharedContext context, InsertionContext insertionContext, CharacterToken characterToken) {
		int ch = characterToken.getCharacter();
		if (characterToken.getCharacter() == 0) {
			context.parseError();
		}
		// TODO: Handle any active formatting elements, and unset frameset-ok
		InsertionLogic.insertCharacters(context, insertionContext, new int[] { ch });
	}

	private boolean handleEndTag(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
		switch (token.getName()) {
		case "body":
			handleBodyEndTag(context, insertionContext, token);
			return true;
		default:
			return false;
		}
	}

	private void handleBodyEndTag(SharedContext context, InsertionContext insertionContext, EndTagToken token) {
		// TODO: Parse errors
		context.setInsertionMode(afterBodyInsertionMode);
	}

}
