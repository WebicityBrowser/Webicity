package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.ReaderHandle;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;

public class MarkupDeclarationOpenState implements TokenizeState {

	private final DoctypeState doctypeState;

	public MarkupDeclarationOpenState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.doctypeState = initializer.getTokenizeState(DoctypeState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		ReaderHandle reader = parsingContext.readerHandle();
		parsingContext.readerHandle().unread(ch);
		if (reader.lookahead(7).equalsIgnoreCase("DOCTYPE")) {
			reader.eat(7);
			context.setTokenizeState(doctypeState);
		} else {
			// TODO
			throw new UnsupportedOperationException();
		}
	}

}
