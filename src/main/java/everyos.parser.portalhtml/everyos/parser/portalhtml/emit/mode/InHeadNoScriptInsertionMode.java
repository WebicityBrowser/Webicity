package everyos.parser.portalhtml.emit.mode;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.emit.InsertionMode;
import everyos.parser.portalhtml.emit.util.StackUtil;
import everyos.parser.portalhtml.emit.util.TokenUtil;
import everyos.parser.portalhtml.tokens.CommentToken;
import everyos.parser.portalhtml.tokens.DoctypeToken;
import everyos.parser.portalhtml.tokens.EndTagToken;
import everyos.parser.portalhtml.tokens.StartTagToken;
import everyos.parser.portalhtml.tokens.Token;

public class InHeadNoScriptInsertionMode implements InsertionMode {

	private final HTMLParserContext context;
	
	private final InBodyInsertionMode inBodyInsertionMode;
	private final InHeadInsertionMode inHeadInsertionMode;

	public InHeadNoScriptInsertionMode(HTMLParserContext context, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.context = context;
		this.inBodyInsertionMode = context.getInsertionMode(InBodyInsertionMode.class);
		this.inHeadInsertionMode = context.getInsertionMode(InHeadInsertionMode.class);
	}
	
	@Override
	public void emit(Token token) {
		if (token instanceof DoctypeToken) {
			context.recordError(ParseError.GENERIC);
			return;
		} else if (
			TokenUtil.isWhiteSpaceToken(token) ||
			token instanceof CommentToken
		) {
			inHeadInsertionMode.emit(token);
			return;
		}
		
		if (token instanceof StartTagToken) {
			if (processStartTagToken((StartTagToken) token)) {
				return;
			}
		} else if (token instanceof EndTagToken) {
			if (processEndTagToken((EndTagToken) token)) {
				return;
			}
		}
		
		context.recordError(ParseError.GENERIC);
		StackUtil.pop(context);
		context.setCurrentInsertionMode(inHeadInsertionMode);
		inHeadInsertionMode.emit(token);
	}

	private boolean processStartTagToken(StartTagToken token) {
		switch (token.getName()) {
		case "html":
			inBodyInsertionMode.emit(token);
			return true;
		case "basefont":
		case "bgsound":
		case "link":
		case "meta":
		case "noframes":
		case "style":
			inHeadInsertionMode.emit(token);
			return true;
		case "head":
		case "noscript":
			context.recordError(ParseError.GENERIC);
			return true;
		default:
			return false;
		}
	}

	private boolean processEndTagToken(EndTagToken token) {
		switch (token.getName()) {
		case "noscript":
			StackUtil.pop(context);
			context.setCurrentInsertionMode(inHeadInsertionMode);
		case "br":
			return false;
		default:
			context.recordError(ParseError.GENERIC);
			return true;
		}
	}

}
