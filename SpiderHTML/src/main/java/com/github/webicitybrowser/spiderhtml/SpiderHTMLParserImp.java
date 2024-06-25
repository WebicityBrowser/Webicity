package com.github.webicitybrowser.spiderhtml;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

import com.github.webicitybrowser.spec.html.parse.HTMLParser;
import com.github.webicitybrowser.spec.html.parse.HTMLTreeBuilder;
import com.github.webicitybrowser.spec.html.parse.ParserSettings;
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
	public void parse(Reader inputReader, HTMLTreeBuilder treeBuilder, ParserSettings settings) throws IOException {
		PushbackReader reader = new PushbackReader(inputReader, 32);
		ParsingInitializer parsingInitializer = new ParsingInitializer(settings);
		SharedContext sharedContext = new SharedContext(ctx -> new InsertionContext(ctx, treeBuilder, settings));
		ParsingContext parsingContext = new ParsingContext(reader);
		
		initializeContext(parsingInitializer, sharedContext);
		
		while (true) {
			TokenizeState tokenizeState = sharedContext.getTokenizeState();
			if (tokenizeState == null) {
				break;
			}
			
			int ch = reader.read();
			if (ch == '\r') {
				ch = '\n';
			} else if (ch == '\n' && parsingContext.readerHandle().peek() == '\r') {
				reader.read();
			}
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
