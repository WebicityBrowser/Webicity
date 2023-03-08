package everyos.parser.portalhtml.context;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import everyos.parser.portalhtml.BufferPositionTracker;
import everyos.parser.portalhtml.HTMLParser;
import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.UnicodeDictionary;
import everyos.parser.portalhtml.emit.EmitterLogic;
import everyos.parser.portalhtml.emit.InsertionMode;
import everyos.parser.portalhtml.emit.mode.InitialInsertionMode;
import everyos.parser.portalhtml.tokenize.AttributeValueDoubleQuotedState;
import everyos.parser.portalhtml.tokenize.AttributeValueSingleQuotedState;
import everyos.parser.portalhtml.tokenize.AttributeValueUnquotedState;
import everyos.parser.portalhtml.tokenize.TokenizeState;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.EndTagToken;
import everyos.parser.portalhtml.tokens.StartTagToken;
import everyos.parser.portalhtml.tokens.Token;
import everyos.parser.portalhtml.tree.HTMLTreeBuilder;

public class HTMLParserContext {
	
	private final PushbackReader reader;
	private final HTMLTreeBuilder treeBuilder;
	private final BufferPositionTracker bufferPositionTracker;
	private final UnicodeDictionary unicodeDictionary;
	
	private final Map<Class<? extends TokenizeState>, TokenizeState> tokenizeStateCache = new HashMap<>();
	private final Map<Class<? extends InsertionMode>, InsertionMode> insertionModeCache = new HashMap<>();
	private final InsertionContext insertionContext = new InsertionContext();
	
	private InsertionMode insertionMode = getInsertionMode(InitialInsertionMode.class);
	
	private StringBuffer temporaryBuffer;
	private Token currentToken;
	private TokenizeState returnState;
	private StartTagToken lastStartTagToken;
	private int characterReferenceCode;
	
	public HTMLParserContext(
		PushbackReader reader, HTMLTreeBuilder treeBuilder, BufferPositionTracker bufferPositionTracker, UnicodeDictionary unicodeDictionary
	) {
		this.reader = reader;
		this.treeBuilder = treeBuilder;
		this.bufferPositionTracker = bufferPositionTracker;
		this.unicodeDictionary = unicodeDictionary;
	}

	public void setReturnState(TokenizeState dataState) {
		returnState = dataState;
	}
	
	public TokenizeState getReturnState() {
		return returnState;
	}

	public void setCurrentToken(Token token) {
		this.currentToken = token;
	}

	public Token getCurrentToken() {
		return this.currentToken;
	}

	public void setTemporaryBuffer(String string) {
		temporaryBuffer = new StringBuffer();
	}

	public StringBuffer getTemporaryBuffer() {
		return temporaryBuffer;
	}
	
	public void setCharacterReferenceCode(int i) {
		this.characterReferenceCode = i;
	}
	
	public int getCharacterReferenceCode() {
		return this.characterReferenceCode;
	}
	
	public void setCurrentInsertionMode(InsertionMode mode) {
		this.insertionMode = mode;
	}
	
	public InsertionMode getCurrentInsertionMode() {
		return this.insertionMode;
	}
	
	public HTMLTreeBuilder getTreeBuilder() {
		return this.treeBuilder;
	}
	
	public UnicodeDictionary getUnicodeDictionary() {
		return this.unicodeDictionary;
	}
	
	public InsertionContext getInsertionContext() {
		return this.insertionContext;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends TokenizeState> T getTokenizeState(Class<T> stateClass) {
		if (!tokenizeStateCache.containsKey(stateClass)) {
			try {
				Consumer<TokenizeState> putReference = state -> tokenizeStateCache.put(stateClass, state);
				stateClass
					.getConstructor(HTMLParserContext.class, Consumer.class)
					.newInstance(this, putReference);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return (T) tokenizeStateCache.get(stateClass);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends InsertionMode> T getInsertionMode(Class<T> modeClass) {
		if (!insertionModeCache.containsKey(modeClass)) {
			try {
				Consumer<InsertionMode> putReference = state -> insertionModeCache.put(modeClass, state);
				modeClass
					.getConstructor(HTMLParserContext.class, Consumer.class)
					.newInstance(this, putReference);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return (T) insertionModeCache.get(modeClass);
	}
	
	public void emit(Token token) {
		if (token instanceof StartTagToken) {
			lastStartTagToken = (StartTagToken) token;
		}
		
		EmitterLogic.emit(this, token);
	}

	public void recordError(ParseError error) {
		// TODO Auto-generated method stub
		/*if (error == ParseError.UNKNOWN_NAMED_CHARACTER_REFERENCE) {
			return;
		}
		throw new RuntimeException();*/
	}

	public void unread(int ch) {
		try {
			reader.unread(ch);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		bufferPositionTracker.backtrack();
	}
	
	public boolean currentTokenIsAppropriateEndTagToken() {
		return
			lastStartTagToken != null &&
			currentToken instanceof EndTagToken &&
			((EndTagToken) currentToken).getName().equals(lastStartTagToken.getName());
	}

	public String lookahead(int num) {
		try {
			int[] chars = new int[num];
			for (int i = 0; i < num; i++) {
				chars[i] = reader.read();
			}
			for (int i = num - 1; i >= 0; i--) {
				reader.unread(chars[i]);
			}
			return new String(chars, 0, num);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void eat(int num) {
		try {
			for (int i = 0; i < num; i++) {
					bufferPositionTracker.recordCharacter(reader.read());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean isConsumedAsPartOfAnAttribute() {
		return
			(returnState instanceof AttributeValueUnquotedState) ||
			(returnState instanceof AttributeValueSingleQuotedState) ||
			(returnState instanceof AttributeValueDoubleQuotedState);
	}

	public void flushCodePointsConsumedAsACharacterReference() {
		boolean isPartOfAnAttribute = isConsumedAsPartOfAnAttribute();
		for (int i = temporaryBuffer.length() - 1; i >= 0; i--) {
			int ch = temporaryBuffer.codePointAt(i);
			if (isPartOfAnAttribute) {
				((StartTagToken) currentToken).appendToAttributeValue(ch);
			} else {
				emit(new CharacterToken(ch));
			}
		}
	}

	public HTMLParser getSpeculativeHTMLParser() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
