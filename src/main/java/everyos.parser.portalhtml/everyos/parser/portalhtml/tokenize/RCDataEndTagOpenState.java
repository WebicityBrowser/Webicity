package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.EndTagToken;
import everyos.parser.portalhtml.util.CharUtil;

public class RCDataEndTagOpenState implements TokenizeState {
	
	private final HTMLParserContext context;
	
	private final RCDataEndTagNameState rcdataEndTagNameState;
	private final RCDataState rcdataState;

	public RCDataEndTagOpenState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.rcdataEndTagNameState = context.getTokenizeState(RCDataEndTagNameState.class);
		this.rcdataState = context.getTokenizeState(RCDataState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		if (CharUtil.isASCIIAlpha(ch)) {
			context.setCurrentToken(new EndTagToken());
			context.unread(ch);
			return rcdataEndTagNameState;
		}
		
		context.emit(new CharacterToken('<'));
		context.emit(new CharacterToken('/'));
		context.unread(ch);
		return rcdataState;
	}

}
