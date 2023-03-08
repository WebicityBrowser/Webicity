package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;

public class ScriptDataEscapeStartState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final ScriptDataEscapeStartDashState scriptDataEscapeStartDashState;
	private final ScriptDataState scriptDataState;

	public ScriptDataEscapeStartState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		
		this.scriptDataEscapeStartDashState = context.getTokenizeState(ScriptDataEscapeStartDashState.class);
		this.scriptDataState = context.getTokenizeState(ScriptDataState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		if (ch == '-') {
			context.emit(new CharacterToken('-'));
			return scriptDataEscapeStartDashState;
		} else {
			context.unread(ch);
			return scriptDataState;
		}
	}

}
