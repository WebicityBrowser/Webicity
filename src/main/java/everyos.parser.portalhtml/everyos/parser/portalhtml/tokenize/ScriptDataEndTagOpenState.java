package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.EndTagToken;
import everyos.parser.portalhtml.util.CharUtil;

public class ScriptDataEndTagOpenState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final ScriptDataEndTagNameState scriptDataEndTagNameState;
	private final ScriptDataState scriptDataState;

	public ScriptDataEndTagOpenState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.scriptDataEndTagNameState = context.getTokenizeState(ScriptDataEndTagNameState.class);
		this.scriptDataState = context.getTokenizeState(ScriptDataState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		if (CharUtil.isASCIIAlpha(ch)) {
			context.setCurrentToken(new EndTagToken());
			context.unread(ch);
			return scriptDataEndTagNameState;
		}
		
		context.emit(new CharacterToken('<'));
		context.emit(new CharacterToken('/'));
		context.unread(ch);
		return scriptDataState;
	}

}
