package everyos.parser.portalhtml;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

import everyos.parser.portalhtml.context.InsertionContext;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokenize.DataState;
import everyos.parser.portalhtml.tokenize.TokenizeState;
import everyos.parser.portalhtml.tree.HTMLLeaf;
import everyos.parser.portalhtml.tree.HTMLTreeBuilder;

public class HTMLParser {

	public <T extends HTMLLeaf> void parse(
		Reader inputReader, HTMLTreeBuilder treeBuilder, UnicodeDictionary unicodeDictionary
	) throws IOException {
		try {
			PushbackReader reader = new PushbackReader(inputReader, 16);
			BufferPositionTracker bufferPositionTracker = new BufferPositionTracker();
			HTMLParserContext context = new HTMLParserContext(reader, treeBuilder, bufferPositionTracker, unicodeDictionary);
			InsertionContext insertionContext = context.getInsertionContext();
			
			TokenizeState tokenizeState = context.getTokenizeState(DataState.class);
			
			while (tokenizeState != null) {
				int ch = reader.read();
				bufferPositionTracker.recordCharacter(ch);
				tokenizeState = tokenizeState.process(ch);
				tokenizeState = adjustTokenizeState(tokenizeState, insertionContext);
				//System.out.println(tokenizeState);
			}
		} catch (RuntimeException e) {
			if (e.getCause() instanceof IOException) {
				throw (IOException) e.getCause();
			}
			
			throw e;
		}
	}

	private TokenizeState adjustTokenizeState(TokenizeState tokenizeState, InsertionContext insertionContext) {
		if (insertionContext.getNextTokenizeStateOverride() != null) {
			tokenizeState = insertionContext.getNextTokenizeStateOverride();
			insertionContext.setNextTokenizeStateOverride(null);
		}
		
		return tokenizeState;
	}
	
}
