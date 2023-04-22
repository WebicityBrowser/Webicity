package com.github.webicitybrowser.spiderhtml.tokenize;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.TagToken;
import com.github.webicitybrowser.spiderhtml.util.ASCIIUtil;

public class TagNameState implements TokenizeState {

	private final BeforeAttributeNameState beforeAttributeNameState;
	private final SelfClosingStartTagState selfClosingStartTagState;
	private final DataState dataState;

	public TagNameState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.beforeAttributeNameState = initializer.getTokenizeState(BeforeAttributeNameState.class);
		this.selfClosingStartTagState = initializer.getTokenizeState(SelfClosingStartTagState.class);
		this.dataState = initializer.getTokenizeState(DataState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) {
		TagToken currentTagToken = parsingContext.getCurrentToken(TagToken.class);
		
		// TODO
		switch (ch) {
		case '\t':
		case '\n':
		case '\f':
		case ' ':
			context.setTokenizeState(beforeAttributeNameState);
			break;
		case '/':
			context.setTokenizeState(selfClosingStartTagState);
			break;
		case '>':
			context.setTokenizeState(dataState);
			context.emit(currentTagToken);
			break;
		default:
			currentTagToken.appendToName(ASCIIUtil.toASCIILowerCase(ch));
		}
	}

}
