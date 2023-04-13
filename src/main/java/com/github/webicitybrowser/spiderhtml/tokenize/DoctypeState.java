package com.github.webicitybrowser.spiderhtml.tokenize;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;

public class DoctypeState implements TokenizeState {

	private final BeforeDoctypeNameState beforeDoctypeNameState;

	public DoctypeState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.beforeDoctypeNameState = initializer.getTokenizeState(BeforeDoctypeNameState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) {
		switch (ch) {
		case '\t':
		case '\n':
		case '\f':
		case ' ':
			context.setTokenizeState(beforeDoctypeNameState);
			break;
		default:
			// TODO
			throw new UnsupportedOperationException();
		}
	}

}
