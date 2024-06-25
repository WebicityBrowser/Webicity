package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;

public class ScriptDataEscapedLessThanSignState implements TokenizeState {

	public ScriptDataEscapedLessThanSignState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
