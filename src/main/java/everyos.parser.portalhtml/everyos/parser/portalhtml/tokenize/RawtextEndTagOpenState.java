package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.EndTagToken;
import everyos.parser.portalhtml.util.CharUtil;

public class RawtextEndTagOpenState implements TokenizeState {
	
	private final HTMLParserContext context;
	
	private final RawtextEndTagNameState rawtextEndTagNameState;

	private final RawtextState rawtextState;

	public RawtextEndTagOpenState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.rawtextEndTagNameState = context.getTokenizeState(RawtextEndTagNameState.class);
		this.rawtextState = context.getTokenizeState(RawtextState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		if (CharUtil.isASCIIAlpha(ch)) {
			context.setCurrentToken(new EndTagToken());
			context.unread(ch);
			return rawtextEndTagNameState;
		}
		
		context.emit(new CharacterToken('<'));
		context.emit(new CharacterToken('/'));
		context.unread(ch);
		return rawtextState;
	}

}
