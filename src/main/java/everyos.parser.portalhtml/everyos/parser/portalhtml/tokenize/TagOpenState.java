package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.CommentToken;
import everyos.parser.portalhtml.tokens.EOFToken;
import everyos.parser.portalhtml.tokens.StartTagToken;
import everyos.parser.portalhtml.util.CharUtil;

public class TagOpenState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final MarkupDeclarationOpenState markupDeclarationOpenState;
	private final EndTagOpenState endTagOpenState;
	private final BogusCommentState bogusCommentState;
	private final TagNameState tagNameState;
	private final DataState dataState;

	public TagOpenState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.markupDeclarationOpenState = context.getTokenizeState(MarkupDeclarationOpenState.class);
		this.endTagOpenState = context.getTokenizeState(EndTagOpenState.class);
		this.bogusCommentState = context.getTokenizeState(BogusCommentState.class);
		this.tagNameState = context.getTokenizeState(TagNameState.class);
		this.dataState = context.getTokenizeState(DataState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		if (CharUtil.isASCIIAlpha(ch)) {
			context.setCurrentToken(new StartTagToken());
			context.unread(ch);
			return tagNameState;
		}
		
		switch (ch) {
			case '!':
				return markupDeclarationOpenState;
				
			case '/':
				return endTagOpenState;
				
			case '?':
				context.recordError(ParseError.UNEXPECTED_QUESTION_MARK_INSTEAD_OF_TAG_NAME);
				context.setCurrentToken(new CommentToken());
				context.unread(ch);
				return bogusCommentState;
				
			case -1:
				context.recordError(ParseError.EOF_BEFORE_TAG_NAME);
				context.emit(new CharacterToken('<'));
				context.emit(new EOFToken());
				return null;
				
			default:
				break;
		}
		
		context.recordError(ParseError.INVALID_FIRST_CHARACTER_OF_TAG_NAME);
		context.emit(new CharacterToken('<'));
		context.unread(ch);
		return dataState;
	}

}
