package com.github.webicitybrowser.spiderhtml.tokenize;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;
import com.github.webicitybrowser.spiderhtml.token.TagToken;

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
			// TODO: End tags will crash the parser if we get here
			context.setTokenizeState(beforeAttributeNameState);
			break;
		case '/':
			context.setTokenizeState(selfClosingStartTagState);
			break;
		case '>':
			context.setTokenizeState(dataState);
			context.emit(currentTagToken);
			break;
		case 0:
			context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
			currentTagToken.appendToName('\uFFFD');
			break;
		case -1:
			context.recordError(ParseError.EOF_IN_TAG);
			context.emit(new EOFToken());
			break;
		default:
			currentTagToken.appendToName(ASCIIUtil.toASCIILowerCase(ch));
		}
	}

}
