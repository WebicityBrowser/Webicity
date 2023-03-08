package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;

public class ScriptDataDoubleEscapedLessThanSignState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final ScriptDataDoubleEscapeEndState scriptDataDoubleEscapeEndState;
	private final ScriptDataDoubleEscapedState scriptDataDoubleEscapedState;

	public ScriptDataDoubleEscapedLessThanSignState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.scriptDataDoubleEscapeEndState = context.getTokenizeState(ScriptDataDoubleEscapeEndState.class);
		this.scriptDataDoubleEscapedState = context.getTokenizeState(ScriptDataDoubleEscapedState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch(ch) {
			case '/':
				context.setTemporaryBuffer("");
				context.emit(new CharacterToken('/'));
				return scriptDataDoubleEscapeEndState;
			
			default:
				context.unread(ch);
				return scriptDataDoubleEscapedState;
		}
	}

}
