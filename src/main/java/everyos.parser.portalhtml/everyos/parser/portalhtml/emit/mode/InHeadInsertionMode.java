package everyos.parser.portalhtml.emit.mode;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.InsertionContext;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.emit.InsertionLocation;
import everyos.parser.portalhtml.emit.InsertionMode;
import everyos.parser.portalhtml.emit.util.StackUtil;
import everyos.parser.portalhtml.emit.util.TokenUtil;
import everyos.parser.portalhtml.emit.util.TreeUtil;
import everyos.parser.portalhtml.tokenize.ScriptDataState;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.CommentToken;
import everyos.parser.portalhtml.tokens.DoctypeToken;
import everyos.parser.portalhtml.tokens.EndTagToken;
import everyos.parser.portalhtml.tokens.StartTagToken;
import everyos.parser.portalhtml.tokens.Token;
import everyos.parser.portalhtml.tree.HTMLLeaf;

public class InHeadInsertionMode implements InsertionMode {

	private final HTMLParserContext context;
	
	private final InBodyInsertionMode inBodyInsertionMode;
	private final InHeadNoScriptInsertionMode inHeadNoScriptInsertionMode;
	private final TextInsertionMode textInsertionMode;
	private final AfterHeadInsertionMode afterHeadInsertionMode;

	private final ScriptDataState scriptDataState;

	public InHeadInsertionMode(HTMLParserContext context, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.context = context;
		this.inBodyInsertionMode = context.getInsertionMode(InBodyInsertionMode.class);
		this.inHeadNoScriptInsertionMode = context.getInsertionMode(InHeadNoScriptInsertionMode.class);
		this.textInsertionMode = context.getInsertionMode(TextInsertionMode.class);
		this.afterHeadInsertionMode = context.getInsertionMode(AfterHeadInsertionMode.class);
		this.scriptDataState = context.getTokenizeState(ScriptDataState.class);
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
		
		StackUtil.pop(context);
		context.setCurrentInsertionMode(afterHeadInsertionMode);
		afterHeadInsertionMode.emit(token);
	}

	private boolean processStartTagToken(StartTagToken token) {
		switch (token.getName()) {
		case "html":
			inBodyInsertionMode.emit(token);
			return true;
		case "base":
		case "basefont":
		case "bgsound":
		case "link":
			TreeUtil.insertHTMLElement(context, token);
			StackUtil.pop(context);
			token.acknowledgeSelfClosingFlag();
			return true;
		case "meta":
			TreeUtil.insertHTMLElement(context, token);
			StackUtil.pop(context);
			token.acknowledgeSelfClosingFlag();
			// TODO
			return true;
		case "title":
			TreeUtil.parseGenericRCDATA(context, token);
			return true;
		// TODO: noscript if scripting enabled
		case "noframes":
		case "style":
			TreeUtil.parseGenericRawText(context, token);
			return true;
		case "noscript":
			TreeUtil.insertHTMLElement(context, token);
			context.setCurrentInsertionMode(inHeadNoScriptInsertionMode);
			return true;
		case "script":
			processScriptStart(token);
			return true;
		case "template":
			TreeUtil.insertHTMLElement(context, token);
			// TODO
			return true;
		case "head":
			context.recordError(ParseError.GENERIC);
			return true;
		default:
			return false;
		}
	}
	
	private boolean processEndTagToken(EndTagToken token) {
		switch (token.getName()) {
		case "head":
			StackUtil.pop(context);
			context.setCurrentInsertionMode(afterHeadInsertionMode);
			return true;
		case "body":
		case "html":
		case "br":
			return false;
		case "template":
			// TODO
		default:
			context.recordError(ParseError.GENERIC);
			return true;
		}
	}

	private void processScriptStart(StartTagToken token) {
		InsertionContext insertionContext = context.getInsertionContext();
		InsertionLocation adjustedInsertionLocation =
			TreeUtil.getAppropriatePlaceForInsertingNode(context, null);
		HTMLLeaf element = TreeUtil.createAnElementForToken(
			context,
			token,
			TreeUtil.HTML_NAMESPACE,
			adjustedInsertionLocation.parent());
		// TODO
		adjustedInsertionLocation.parent()
			.insert(adjustedInsertionLocation.location(), element);
		insertionContext.getOpenElementStack().push(element);
		insertionContext.setNextTokenizeStateOverride(scriptDataState);
		insertionContext.setOriginalInsertionMode(this);
		context.setCurrentInsertionMode(textInsertionMode);
	}

}
