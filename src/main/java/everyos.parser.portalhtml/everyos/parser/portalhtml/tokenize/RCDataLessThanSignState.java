package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;

public class RCDataLessThanSignState implements TokenizeState {
	
	private final HTMLParserContext context;
	
	private final RCDataEndTagOpenState rcdataEndTagOpenState;
	private final RCDataState rcdataState;

	public RCDataLessThanSignState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.rcdataEndTagOpenState = context.getTokenizeState(RCDataEndTagOpenState.class);
		this.rcdataState = context.getTokenizeState(RCDataState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		if (ch == '/') {
			context.setTemporaryBuffer("");
			return rcdataEndTagOpenState;
		}
		
		context.emit(new CharacterToken('<'));
		context.unread(ch);
		return rcdataState;
	}

}
