package everyos.parser.portalhtml.emit.mode;

import java.util.Stack;
import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.InsertionContext;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.emit.InsertionMode;
import everyos.parser.portalhtml.emit.util.TokenUtil;
import everyos.parser.portalhtml.emit.util.TreeUtil;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.CommentToken;
import everyos.parser.portalhtml.tokens.DoctypeToken;
import everyos.parser.portalhtml.tokens.EndTagToken;
import everyos.parser.portalhtml.tokens.StartTagToken;
import everyos.parser.portalhtml.tokens.Token;
import everyos.parser.portalhtml.tree.HTMLLeaf;

public class AfterHeadInsertionMode implements InsertionMode {

	private final HTMLParserContext context;
	
	private final InBodyInsertionMode inBodyInsertionMode;
	private final InHeadInsertionMode inHeadInsertionMode;

	public AfterHeadInsertionMode(HTMLParserContext context, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.context = context;
		this.inBodyInsertionMode = context.getInsertionMode(InBodyInsertionMode.class);
		this.inHeadInsertionMode = context.getInsertionMode(InHeadInsertionMode.class);
	}
	
	@Override
	public void emit(Token token) {
		if (TokenUtil.isWhiteSpaceToken(token)) {
			TreeUtil.insertCharacter(context, (CharacterToken) token);
			return;
		} else if (token instanceof CommentToken) {
			TreeUtil.insertAComment(context, (CommentToken) token, null);
			return;
		} else if (token instanceof DoctypeToken) {
			context.recordError(ParseError.GENERIC);
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
		
		TreeUtil.insertHTMLElement(context, new StartTagToken("body"));
		context.setCurrentInsertionMode(inBodyInsertionMode);
		inBodyInsertionMode.emit(token);
	}

	private boolean processStartTagToken(StartTagToken token) {
		switch (token.getName()) {
		case "html":
			inBodyInsertionMode.emit(token);
			return true;
		case "body":
			TreeUtil.insertHTMLElement(context, token);
			// TODO
			context.setCurrentInsertionMode(inBodyInsertionMode);
			return true;
		case "frameset":
			// TODO
			return true;
		case "base":
		case "basefont":
		case "bgsound":
		case "link":
		case "meta":
		case "noframes":
		case "script":
		case "style":
		case "template":
		case "title": {
			InsertionContext insertionContext = context.getInsertionContext();
			context.recordError(ParseError.GENERIC);
			HTMLLeaf headElementPointer = insertionContext.getHeadElementPointer();
			Stack<HTMLLeaf> openElementStack = insertionContext.getOpenElementStack();
			openElementStack.push(headElementPointer);
			inHeadInsertionMode.emit(token);
			while (openElementStack.pop() != headElementPointer);
			return true;
		}
		case "head":
			context.recordError(ParseError.GENERIC);
			return true;
		default:
			return false;
		}
	}

	private boolean processEndTagToken(EndTagToken token) {
		switch (token.getName()) {
		case "template":
			inHeadInsertionMode.emit(token);
			return true;
		case "body":
		case "html":
		case "br":
			return false;
		default:
			context.recordError(ParseError.GENERIC);
			return true;
		}
	}

}
