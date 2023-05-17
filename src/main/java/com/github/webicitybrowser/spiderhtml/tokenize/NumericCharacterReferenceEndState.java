package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.TokenizeLogic;

public class NumericCharacterReferenceEndState implements TokenizeState {

	public NumericCharacterReferenceEndState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		parsingContext.readerHandle().unread(ch);
		int refCode = parsingContext.getCharacterReferenceCode();
		// TODO: Handle special reference code cases
		parsingContext.resetTemporaryBuffer();
		parsingContext.appendToTemporaryBuffer(refCode);
		TokenizeLogic.flushCodePointsConsumedAsACharacterReference(context, parsingContext);
		context.setTokenizeState(context.getReturnState());
	}

}
