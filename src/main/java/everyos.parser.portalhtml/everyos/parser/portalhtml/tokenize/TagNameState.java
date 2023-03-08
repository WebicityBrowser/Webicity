package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.EOFToken;
import everyos.parser.portalhtml.tokens.TagToken;
import everyos.parser.portalhtml.util.CharUtil;

public class TagNameState implements TokenizeState {
	
	private final HTMLParserContext context;
	
	private final BeforeAttributeNameState beforeAttributeNameState;
	private final SelfClosingStartTagState selfClosingStartTagState;
	private final DataState dataState;

	public TagNameState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.beforeAttributeNameState = context.getTokenizeState(BeforeAttributeNameState.class);
		this.selfClosingStartTagState = context.getTokenizeState(SelfClosingStartTagState.class);
		this.dataState = context.getTokenizeState(DataState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
			case '\t':
			case '\n':
			case '\f':
			case ' ':
				return beforeAttributeNameState;
				
			case '/':
				return selfClosingStartTagState;
				
			case '>':
				context.emit(context.getCurrentToken());
				return dataState;
				
			case 0:
				context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
				((TagToken) context.getCurrentToken()).appendToName('\uFFFD');
				return this;
				
			case -1:
				context.recordError(ParseError.EOF_IN_TAG);
				context.emit(new EOFToken());
				return null;
				
			default:
				((TagToken) context.getCurrentToken())
					.appendToName(CharUtil.toASCIILowerCase(ch));
				return this;
		}
	}

}
