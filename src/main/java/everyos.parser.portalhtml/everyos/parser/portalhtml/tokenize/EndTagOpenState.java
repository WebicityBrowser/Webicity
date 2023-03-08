package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.CommentToken;
import everyos.parser.portalhtml.tokens.EOFToken;
import everyos.parser.portalhtml.tokens.EndTagToken;
import everyos.parser.portalhtml.util.CharUtil;

public class EndTagOpenState implements TokenizeState {
	
	private final HTMLParserContext context;
	
	private final TagNameState tagNameState;
	private final DataState dataState;
	private final BogusCommentState bogusCommentState;

	public EndTagOpenState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.tagNameState = context.getTokenizeState(TagNameState.class);
		this.dataState = context.getTokenizeState(DataState.class);
		this.bogusCommentState = context.getTokenizeState(BogusCommentState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		if (CharUtil.isASCIIAlpha(ch)) {
			context.setCurrentToken(new EndTagToken());
			context.unread(ch);
			return tagNameState;
		}
		
		switch (ch) {
			case '>':
				context.recordError(ParseError.MISSING_END_TAG_NAME);
				return dataState;
				
			case -1:
				context.recordError(ParseError.EOF_BEFORE_TAG_NAME);
				context.emit(new CharacterToken('<'));
				context.emit(new CharacterToken('/'));
				context.emit(new EOFToken());
				return null;
				
			default:
				context.recordError(ParseError.INVALID_FIRST_CHARACTER_OF_TAG_NAME);
				context.setCurrentToken(new CommentToken());
				context.unread(ch);
				return bogusCommentState;
		}
	}

}
