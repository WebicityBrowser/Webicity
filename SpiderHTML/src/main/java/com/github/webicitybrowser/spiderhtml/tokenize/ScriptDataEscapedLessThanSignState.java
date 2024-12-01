package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;

public class ScriptDataEscapedLessThanSignState implements TokenizeState {

	private final ScriptDataEndTagOpenState scriptDataEscapedEndTagOpenState;
	private final ScriptDataDoubleEscapeStartState scriptDataDoubleEscapeStartState;
	private final ScriptDataEscapedState scriptDataEscapedState;

	public ScriptDataEscapedLessThanSignState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.scriptDataEscapedEndTagOpenState = initializer.getTokenizeState(ScriptDataEndTagOpenState.class);
		this.scriptDataDoubleEscapeStartState = initializer.getTokenizeState(ScriptDataDoubleEscapeStartState.class);
		this.scriptDataEscapedState = initializer.getTokenizeState(ScriptDataEscapedState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		if (ch == '/') {
			parsingContext.resetTemporaryBuffer();
			context.setTokenizeState(scriptDataEscapedEndTagOpenState);
		} else if (ASCIIUtil.isASCIIAlpha(ch)) {
			parsingContext.resetTemporaryBuffer();
			context.emit(new CharacterToken('<'));
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(scriptDataDoubleEscapeStartState);
		} else {
			context.emit(new CharacterToken('<'));
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(scriptDataEscapedState);
		}
	}

}
