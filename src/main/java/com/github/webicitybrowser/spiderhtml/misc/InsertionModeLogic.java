package com.github.webicitybrowser.spiderhtml.misc;

import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.insertion.InsertionMode;
import com.github.webicitybrowser.spiderhtml.insertion.TextInsertionMode;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;
import com.github.webicitybrowser.spiderhtml.tokenize.RCDataState;
import com.github.webicitybrowser.spiderhtml.tokenize.RawTextState;
import com.github.webicitybrowser.spiderhtml.tokenize.TokenizeState;

public class InsertionModeLogic {

	private InsertionModeLogic() {}

	public static void followGenericRawTextElementParsingAlgorithm(
		ParsingInitializer initializer, SharedContext context, InsertionContext insertionContext, StartTagToken token
	) {
		InsertionLogic.insertHTMLElement(insertionContext, token);
		TokenizeState rawTextState = initializer.getTokenizeState(RawTextState.class);
		context.setTokenizeState(rawTextState);
		insertionContext.setOriginalInsertionMode(context.getInsertionMode());
		InsertionMode textMode = initializer.getInsertionMode(TextInsertionMode.class);
		context.setInsertionMode(textMode);
	}

	public static void followGenericRCDataElementParsingAlgorithm(
		ParsingInitializer initializer, SharedContext context, InsertionContext insertionContext, StartTagToken token
	) {
		InsertionLogic.insertHTMLElement(insertionContext, token);
		TokenizeState rcDataState = initializer.getTokenizeState(RCDataState.class);
		context.setTokenizeState(rcDataState);
		insertionContext.setOriginalInsertionMode(context.getInsertionMode());
		InsertionMode textMode = initializer.getInsertionMode(TextInsertionMode.class);
		context.setInsertionMode(textMode);
	}
	
	
	
}
