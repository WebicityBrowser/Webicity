package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLogic;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;
import com.github.webicitybrowser.spiderhtml.token.Token;

public class TextInsertionMode implements InsertionMode {

	public TextInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
	}
	
	@Override
	public void emit(SharedContext context, InsertionContext insertionContext, Token token) {
		// TODO
		if (token instanceof CharacterToken charToken) {
			InsertionLogic.insertCharacters(context, insertionContext, new int[] { charToken.getCharacter() });
		} else if (token instanceof EndTagToken) {
			insertionContext.getOpenElementStack().pop();
			context.setInsertionMode(insertionContext.getOriginalInsertionMode());
		} else {
			throw new UnsupportedOperationException();
		}
	}

}
