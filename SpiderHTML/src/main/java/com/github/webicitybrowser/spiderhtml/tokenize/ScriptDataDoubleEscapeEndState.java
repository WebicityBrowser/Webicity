package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;

public class ScriptDataDoubleEscapeEndState implements TokenizeState {

	private final ScriptDataDoubleEscapedState scriptDataDoubleEscapedState;
	private final ScriptDataEscapedState scriptDataEscapedState;

	public ScriptDataDoubleEscapeEndState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.scriptDataDoubleEscapedState = initializer.getTokenizeState(ScriptDataDoubleEscapedState.class);
		this.scriptDataEscapedState = initializer.getTokenizeState(ScriptDataEscapedState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch(ch) {
		case '\t':
		case '\n':
		case '\f':
		case ' ':
		case '/':
		case '>':
			if (parsingContext.getTemporaryBuffer().equals("script")) {
				context.setTokenizeState(scriptDataEscapedState);
			} else {
				context.setTokenizeState(scriptDataDoubleEscapedState);
			}
			context.emit(new CharacterToken(ch));
		default:
			if (ASCIIUtil.isASCIIAlpha(ch)) {
				parsingContext.appendToTemporaryBuffer(ASCIIUtil.toASCIILowerCase(ch));
				context.emit(new CharacterToken(ch));
			} else {
				parsingContext.readerHandle().unread(ch);
				context.setTokenizeState(scriptDataDoubleEscapedState);
			}
		}
	}

}
