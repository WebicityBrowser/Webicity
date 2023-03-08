package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.TagToken;
import everyos.parser.portalhtml.util.CharUtil;

public class RawtextEndTagNameState implements TokenizeState {

private final HTMLParserContext context;
	
	private final BeforeAttributeNameState beforeAttributeNameState;
	private final SelfClosingStartTagState selfClosingStartTagState;
	private final DataState dataState;
	private final RawtextState rawtextState;

	public RawtextEndTagNameState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.beforeAttributeNameState = context.getTokenizeState(BeforeAttributeNameState.class);
		this.selfClosingStartTagState = context.getTokenizeState(SelfClosingStartTagState.class);
		this.dataState = context.getTokenizeState(DataState.class);
		this.rawtextState = context.getTokenizeState(RawtextState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		if (CharUtil.isASCIIAlpha(ch)) {
			((TagToken) context.getCurrentToken()).appendToName(CharUtil.toASCIILowerCase(ch));
			context.getTemporaryBuffer().appendCodePoint(ch);
			return this;
		}
		
		switch (ch) {
			case '\t':
			case '\n':
			case '\f':
			case ' ':
				if (context.currentTokenIsAppropriateEndTagToken()) {
					return beforeAttributeNameState;
				}
				break;
				
			case '/':
				if (context.currentTokenIsAppropriateEndTagToken()) {
					context.emit(context.getCurrentToken());
					return selfClosingStartTagState;
				}
				break;
				
			case '>':
				if (context.currentTokenIsAppropriateEndTagToken()) {
					return dataState;
				}
				return dataState;
				
			default:
				break;
		}
		
		context.emit(new CharacterToken('<'));
		context.emit(new CharacterToken('/'));
		String buf = context.getTemporaryBuffer().toString();
		for (int i = 0; i < buf.length(); i++) {
			context.emit(new CharacterToken(buf.codePointAt(i)));
		}
		context.unread(ch);
		return rawtextState;
	}

}
