package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.EndTagToken;
import everyos.parser.portalhtml.util.CharUtil;

public class ScriptDataEscapedEndTagOpenState implements TokenizeState {
	
	private final HTMLParserContext context;
	
	private final ScriptDataEscapedEndTagNameState scriptDataEscapedEndTagNameState;
	private final ScriptDataEscapedState scriptDataEscapedState;

	public ScriptDataEscapedEndTagOpenState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.scriptDataEscapedEndTagNameState = context.getTokenizeState(ScriptDataEscapedEndTagNameState.class);
		this.scriptDataEscapedState = context.getTokenizeState(ScriptDataEscapedState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		if (CharUtil.isASCIIAlpha(ch)) {
			context.setCurrentToken(new EndTagToken());
			context.unread(ch);
			return scriptDataEscapedEndTagNameState;
		}
		
		context.emit(new CharacterToken('<'));
		context.emit(new CharacterToken('/'));
		context.unread(ch);
		return scriptDataEscapedState;
	}

}
