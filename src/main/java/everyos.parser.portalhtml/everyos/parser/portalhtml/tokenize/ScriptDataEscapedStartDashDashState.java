package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;

public class ScriptDataEscapedStartDashDashState implements TokenizeState {

	private final HTMLParserContext context;

	public ScriptDataEscapedStartDashDashState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
	}
	
	@Override
	public TokenizeState process(int ch) {
		// TODO Auto-generated method stub
		return null;
	}

}
