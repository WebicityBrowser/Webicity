package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;

public class CharacterReferenceState implements TokenizeState {

	private final NamedCharacterReferenceState namedCharacterReferenceState;

	public CharacterReferenceState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.namedCharacterReferenceState = initializer.getTokenizeState(NamedCharacterReferenceState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		parsingContext.resetTemporaryBuffer();
		parsingContext.appendToTemporaryBuffer('&');
		if (ASCIIUtil.isASCIIAlpha(ch)) {
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(namedCharacterReferenceState);
		} else {
			// TODO: Support other reference types
			throw new UnsupportedOperationException();
		}
	}

}
