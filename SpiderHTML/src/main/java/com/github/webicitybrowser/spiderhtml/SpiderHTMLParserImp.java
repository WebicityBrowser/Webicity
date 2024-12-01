package com.github.webicitybrowser.spiderhtml;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.github.webicitybrowser.spec.encoding.Decoder;
import com.github.webicitybrowser.spec.html.parse.HTMLParser;
import com.github.webicitybrowser.spec.html.parse.HTMLTreeBuilder;
import com.github.webicitybrowser.spec.html.parse.ParserSettings;
import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.ReaderHandle;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.insertion.InitialInsertionMode;
import com.github.webicitybrowser.spiderhtml.insertion.InsertionMode;
import com.github.webicitybrowser.spiderhtml.tokenize.DataState;
import com.github.webicitybrowser.spiderhtml.tokenize.TokenizeState;

public class SpiderHTMLParserImp implements HTMLParser {

	private final SharedContext sharedContext;
	private final ReaderHandle readerHandle;
	private final ParsingContext parsingContext;
	private final Decoder decoder;

	public SpiderHTMLParserImp(HTMLTreeBuilder treeBuilder, ParserSettings parserSettings) {
		this.sharedContext = new SharedContext(ctx -> new InsertionContext(ctx, treeBuilder, parserSettings));
		initializeContext(parserSettings, sharedContext);
		this.readerHandle = new ReaderHandle(32, 32);
		this.parsingContext = new ParsingContext(readerHandle);
		this.decoder = new Decoder();
	}

	@Override
	public void next(byte[] byteBuffer) throws IOException {
		// TODO: Proper codepoint conversion
		int[] codepoints = decoder.decode(byteBuffer, StandardCharsets.UTF_8);
		readerHandle.pushBuffer(codepoints);
		continueParsing();
	}

	@Override
	public void done() throws IOException {
		readerHandle.finalizeBuffers();
		continueParsing();
		assert sharedContext.getTokenizeState() == null;
	}

	private void continueParsing() throws IOException {
		while (readerHandle.isLookaheadReady() && sharedContext.getTokenizeState() != null) {
			int ch = readerHandle.read();
			if (ch == '\r') {
				ch = '\n';
			} else if (ch == '\n' && readerHandle.peek() == '\r') {
				readerHandle.read();
			}
			sharedContext.getTokenizeState().process(sharedContext, parsingContext, ch);
		}
	}

	private void initializeContext(ParserSettings settings, SharedContext sharedContext) {
		ParsingInitializer parsingInitializer = new ParsingInitializer(settings);

		TokenizeState dataState = parsingInitializer.getTokenizeState(DataState.class);
		sharedContext.setTokenizeState(dataState);
		
		InsertionMode initialInsertionMode = parsingInitializer.getInsertionMode(InitialInsertionMode.class);
		sharedContext.setInsertionMode(initialInsertionMode);
	}

}
