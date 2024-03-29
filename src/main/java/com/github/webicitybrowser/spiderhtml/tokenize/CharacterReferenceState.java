package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.TokenizeLogic;

public class CharacterReferenceState implements TokenizeState {

	private final NamedCharacterReferenceState namedCharacterReferenceState;
	private final NumericCharacterReferenceState numericCharacterReferenceState;

	public CharacterReferenceState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.namedCharacterReferenceState = initializer.getTokenizeState(NamedCharacterReferenceState.class);
		this.numericCharacterReferenceState = initializer.getTokenizeState(NumericCharacterReferenceState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		parsingContext.resetTemporaryBuffer();
		parsingContext.appendToTemporaryBuffer('&');
		if (ASCIIUtil.isASCIIAlphanumeric(ch)) {
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(namedCharacterReferenceState);
		} else if (ch == '#') {
			parsingContext.appendToTemporaryBuffer(ch);
			context.setTokenizeState(numericCharacterReferenceState);
		} else {
			TokenizeLogic.flushCodePointsConsumedAsACharacterReference(context, parsingContext);
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(context.getReturnState());
		}
	}

}
