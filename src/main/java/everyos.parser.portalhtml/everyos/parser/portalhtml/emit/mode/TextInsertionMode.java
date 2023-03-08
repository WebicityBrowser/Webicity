package everyos.parser.portalhtml.emit.mode;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.InsertionContext;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.emit.InsertionMode;
import everyos.parser.portalhtml.emit.util.StackUtil;
import everyos.parser.portalhtml.emit.util.TreeUtil;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.EOFToken;
import everyos.parser.portalhtml.tokens.EndTagToken;
import everyos.parser.portalhtml.tokens.Token;

public class TextInsertionMode implements InsertionMode {

	private final HTMLParserContext context;

	public TextInsertionMode(HTMLParserContext context, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.context = context;
	}
	
	@Override
	public void emit(Token token) {
		InsertionContext insertionContext = context.getInsertionContext();
		if (token instanceof CharacterToken) {
			TreeUtil.insertCharacter(context, (CharacterToken) token);
		} else if (token instanceof EOFToken) {
			context.recordError(ParseError.GENERIC);
			// TODO
			StackUtil.pop(context);
			InsertionMode originalMode = insertionContext.getOriginalInsertionMode();
			context.setCurrentInsertionMode(originalMode);
			originalMode.emit(token);
		} else if (token instanceof EndTagToken) {
			// TODO: Handle script tag
			StackUtil.pop(context);
			InsertionMode originalMode = insertionContext.getOriginalInsertionMode();
			context.setCurrentInsertionMode(originalMode);
		}
	}

}
