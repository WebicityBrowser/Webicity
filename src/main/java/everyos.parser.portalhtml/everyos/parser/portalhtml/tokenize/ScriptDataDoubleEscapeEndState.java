package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.util.CharUtil;

public class ScriptDataDoubleEscapeEndState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final ScriptDataDoubleEscapedState scriptDataDoubleEscapedState;
	private final ScriptDataEscapedState scriptDataEscapedState;

	public ScriptDataDoubleEscapeEndState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.scriptDataDoubleEscapedState = context.getTokenizeState(ScriptDataDoubleEscapedState.class);
		this.scriptDataEscapedState = context.getTokenizeState(ScriptDataEscapedState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
			case '\t':
			case '\n':
			case '\f':
			case ' ':
			case '/':
			case '>':
				context.emit(new CharacterToken(ch));
				if (context.getTemporaryBuffer().toString().equals("script")) {
					return scriptDataEscapedState;
				} else {
					return scriptDataDoubleEscapedState;
				}
				
			default:
				break;
		}
		
		if (CharUtil.isASCIIAlpha(ch)) {
			context.getTemporaryBuffer().appendCodePoint(CharUtil.toASCIILowerCase(ch));
			context.emit(new CharacterToken(ch));
		}
		
		context.unread(ch);
		return scriptDataDoubleEscapedState;
	}

}
