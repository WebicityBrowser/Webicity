package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;

public class RawtextLessThanSignState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final RawtextEndTagOpenState rawtextEndTagOpenState;
	private final RawtextState rawtextState;

	public RawtextLessThanSignState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.rawtextEndTagOpenState = context.getTokenizeState(RawtextEndTagOpenState.class);
		this.rawtextState = context.getTokenizeState(RawtextState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		if (ch == '/') {
			context.setTemporaryBuffer("");
			return rawtextEndTagOpenState;
		} else {
			context.emit(new CharacterToken('<'));
			context.unread(ch);
			return rawtextState;
		}
	}

}
