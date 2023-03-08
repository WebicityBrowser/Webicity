package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.util.CharUtil;

public class ScriptDataEscapedLessThanSignState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final ScriptDataEscapedEndTagOpenState scriptDataEscapedEndTagOpenState;
	private final ScriptDataDoubleEscapeStartState scriptDataDoubleEscapeStartState;
	private final ScriptDataEscapedState scriptDataEscapedState;

	public ScriptDataEscapedLessThanSignState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.scriptDataEscapedEndTagOpenState = context.getTokenizeState(ScriptDataEscapedEndTagOpenState.class);
		this.scriptDataDoubleEscapeStartState = context.getTokenizeState(ScriptDataDoubleEscapeStartState.class);
		this.scriptDataEscapedState = context.getTokenizeState(ScriptDataEscapedState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		if (ch == '/') {
			context.setTemporaryBuffer("");
			return scriptDataEscapedEndTagOpenState;
		}
		
		if (CharUtil.isASCIIAlpha(ch)) {
			context.setTemporaryBuffer("");
			context.emit(new CharacterToken('<'));
			context.unread(ch);
			return scriptDataDoubleEscapeStartState;
		}
		
		context.emit(new CharacterToken('<'));
		context.unread(ch);
		return scriptDataEscapedState;
	}

}
