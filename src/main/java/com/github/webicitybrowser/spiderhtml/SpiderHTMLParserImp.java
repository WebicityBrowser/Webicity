package com.github.webicitybrowser.spiderhtml;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

import com.github.webicitybrowser.spec.html.parse.HTMLParser;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLTreeBuilder;
import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.insertion.InitialInsertionMode;
import com.github.webicitybrowser.spiderhtml.insertion.InsertionMode;
import com.github.webicitybrowser.spiderhtml.tokenize.DataState;
import com.github.webicitybrowser.spiderhtml.tokenize.TokenizeState;

public class SpiderHTMLParserImp implements HTMLParser {

	@Override
	public void parse(Reader inputReader, HTMLTreeBuilder treeBuilder) throws IOException {
		PushbackReader reader = new PushbackReader(inputReader, 16);
		ParsingInitializer parsingInitializer = new ParsingInitializer();
		SharedContext sharedContext = new SharedContext(c -> new InsertionContext(c, treeBuilder));
		ParsingContext parsingContext = new ParsingContext();
		
		initializeContext(parsingInitializer, sharedContext);
		
		while (true) {
			TokenizeState tokenizeState = sharedContext.getTokenizeState();
			if (tokenizeState == null) {
				break;
			}
			
			int ch = reader.read();
			tokenizeState.process(sharedContext, parsingContext, ch);
		}
	}

	private void initializeContext(ParsingInitializer parsingInitializer, SharedContext sharedContext) {
		TokenizeState dataState = parsingInitializer.getTokenizeState(DataState.class);
		sharedContext.setTokenizeState(dataState);
		
		InsertionMode initialInsertionMode = parsingInitializer.getInsertionMode(InitialInsertionMode.class);
		sharedContext.setInsertionMode(initialInsertionMode);
	}

}
