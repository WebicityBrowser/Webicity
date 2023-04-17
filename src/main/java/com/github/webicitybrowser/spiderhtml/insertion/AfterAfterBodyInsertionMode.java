package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;
import com.github.webicitybrowser.spiderhtml.token.Token;
import com.github.webicitybrowser.spiderhtml.util.ASCIIUtil;

public class AfterAfterBodyInsertionMode implements InsertionMode {

	private final InBodyInsertionMode inBodyInsertionMode;

	public AfterAfterBodyInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.inBodyInsertionMode = initializer.getInsertionMode(InBodyInsertionMode.class);
	}
	
	@Override
	public void emit(SharedContext context, InsertionContext insertionContext, Token token) {
		// TODO
		if (
			token instanceof CharacterToken characterToken &&
			ASCIIUtil.isASCIIWhiteSpace(characterToken.getCharacter())
		) {
			inBodyInsertionMode.emit(context, insertionContext, characterToken);
		} else if (token instanceof EOFToken) {
			insertionContext.stopParsing();
		} else {
			throw new UnsupportedOperationException();
		}
	}

}
