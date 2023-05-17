package com.github.webicitybrowser.spiderhtml.insertion;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLocation;
import com.github.webicitybrowser.spiderhtml.misc.InsertionLogic;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.CommentToken;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;
import com.github.webicitybrowser.spiderhtml.token.Token;

public class AfterAfterBodyInsertionMode implements InsertionMode {

	private final InBodyInsertionMode inBodyInsertionMode;

	public AfterAfterBodyInsertionMode(ParsingInitializer initializer, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.inBodyInsertionMode = initializer.getInsertionMode(InBodyInsertionMode.class);
	}
	
	@Override
	public void emit(SharedContext context, InsertionContext insertionContext, Token token) {
		// TODO
		if (token instanceof CommentToken commentToken) {
			Document document = insertionContext.getTreeBuilder().getDocument();
			InsertionLocation position = new InsertionLocation(document, null);
			InsertionLogic.insertComment(insertionContext, commentToken, position);
		} else if (
			token instanceof CharacterToken characterToken &&
			ASCIIUtil.isASCIIWhiteSpace(characterToken.getCharacter())
		) {
			inBodyInsertionMode.emit(context, insertionContext, characterToken);
		} else if (token instanceof EOFToken) {
			insertionContext.stopParsing();
		} else {
			context.recordError();
		}
	}

}
