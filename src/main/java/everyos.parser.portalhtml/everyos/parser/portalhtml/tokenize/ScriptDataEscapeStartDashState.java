package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;

public class ScriptDataEscapeStartDashState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final ScriptDataEscapedStartDashDashState scriptDataEscapedStartDashDashState;
	private final ScriptDataState scriptDataState;

	public ScriptDataEscapeStartDashState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		
		this.scriptDataEscapedStartDashDashState = context.getTokenizeState(ScriptDataEscapedStartDashDashState.class);
		this.scriptDataState = context.getTokenizeState(ScriptDataState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		if (ch == '-') {
			context.emit(new CharacterToken('-'));
			return scriptDataEscapedStartDashDashState;
		} else {
			context.unread(ch);
			return scriptDataState;
		}
	}

}
