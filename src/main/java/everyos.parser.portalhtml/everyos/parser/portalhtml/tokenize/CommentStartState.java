package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;

public class CommentStartState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final CommentStartDashState commentStartDashState;
	private final DataState dataState;
	private final CommentState commentState;

	public CommentStartState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		
		this.commentStartDashState = context.getTokenizeState(CommentStartDashState.class);
		this.dataState = context.getTokenizeState(DataState.class);
		this.commentState = context.getTokenizeState(CommentState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch(ch) {
			case '-':
				return commentStartDashState;
			case '>':
				context.recordError(ParseError.ABRUPT_CLOSING_OF_EMPTY_COMMENT);
				context.emit(context.getCurrentToken());
				return dataState;
			default:
				context.unread(ch);
				return commentState;
		}
	}

}
