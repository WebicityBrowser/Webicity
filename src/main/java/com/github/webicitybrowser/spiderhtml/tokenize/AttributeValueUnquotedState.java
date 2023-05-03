package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;

public class AttributeValueUnquotedState implements TokenizeState {

	private final BeforeAttributeNameState beforeAttributeNameState;
	private final DataState dataState;

	public AttributeValueUnquotedState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.beforeAttributeNameState = initializer.getTokenizeState(BeforeAttributeNameState.class);
		this.dataState = initializer.getTokenizeState(DataState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		// TODO: Character reference
		StartTagToken tagToken = parsingContext.getCurrentToken(StartTagToken.class);
		switch (ch) {
		case '\t':
		case '\n':
		case '\f':
		case ' ':
			context.setTokenizeState(beforeAttributeNameState);
			break;
		case '>':
			context.setTokenizeState(dataState);
			context.emit(tagToken);
			break;
		case 0:
			context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
			context.emit(new CharacterToken('\uFFFD'));
			break;
		case -1:
			context.recordError(ParseError.EOF_IN_TAG);
			context.emit(new EOFToken());
			break;
		case '"':
		case '\'':
		case '<':
		case '=':
		case '`':
			context.recordError(ParseError.UNEXPECTED_CHARACTER_IN_UNQUOTED_ATTRIBUTE);
			// Fall-through
		default:
			tagToken.appendToAttributeValue(ch);
		}
	}

}
