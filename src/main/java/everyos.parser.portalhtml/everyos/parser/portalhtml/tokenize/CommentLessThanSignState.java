package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CommentToken;

public class CommentLessThanSignState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final CommentLessThanSignBangState commentLessThanSignBangState;
	private final CommentState commentState;

	public CommentLessThanSignState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.commentLessThanSignBangState = context.getTokenizeState(CommentLessThanSignBangState.class);
		this.commentState = context.getTokenizeState(CommentState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
		case '!':
			((CommentToken) context.getCurrentToken()).appendToValue(ch);
			return commentLessThanSignBangState;
		case '<':
			((CommentToken) context.getCurrentToken()).appendToValue(ch);
			return this;
		default:
			context.unread(ch);
			return commentState;
		}
	}
	
}
