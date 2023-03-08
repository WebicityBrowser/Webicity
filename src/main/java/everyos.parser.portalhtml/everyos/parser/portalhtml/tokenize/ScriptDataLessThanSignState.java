package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;

public class ScriptDataLessThanSignState implements TokenizeState {

private final HTMLParserContext context;
	
	private final ScriptDataEndTagOpenState scriptDataEndTagOpenState;
	private final ScriptDataEscapeStartState scriptDataEscapeStartState;
	private final ScriptDataState scriptDataState;

	public ScriptDataLessThanSignState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.scriptDataEndTagOpenState = context.getTokenizeState(ScriptDataEndTagOpenState.class);
		this.scriptDataEscapeStartState = context.getTokenizeState(ScriptDataEscapeStartState.class);
		this.scriptDataState = context.getTokenizeState(ScriptDataState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
		case '/':
			context.setTemporaryBuffer("");
			return scriptDataEndTagOpenState;
				
		case '!':
			context.emit(new CharacterToken('<'));
			context.emit(new CharacterToken('!'));
			return scriptDataEscapeStartState;
				
		default:
			context.emit(new CharacterToken('<'));
			context.unread(ch);
			return scriptDataState;
		}
	}

}
