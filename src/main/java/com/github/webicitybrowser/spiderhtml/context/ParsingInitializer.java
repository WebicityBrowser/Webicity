package com.github.webicitybrowser.spiderhtml.context;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParserSettings;
import com.github.webicitybrowser.spiderhtml.insertion.InsertionMode;
import com.github.webicitybrowser.spiderhtml.tokenize.TokenizeState;

public class ParsingInitializer {

	private final Map<Class<? extends TokenizeState>, TokenizeState> tokenizeStateCache = new HashMap<>();
	private final Map<Class<? extends InsertionMode>, InsertionMode> insertionModeCache = new HashMap<>();
	
	private final ParserSettings settings;
	
	public ParsingInitializer(ParserSettings settings) {
		this.settings = settings;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends TokenizeState> T getTokenizeState(Class<T> stateClass) {
		if (!tokenizeStateCache.containsKey(stateClass)) {
			try {
				Consumer<TokenizeState> putReference = state -> tokenizeStateCache.put(stateClass, state);
				stateClass
					.getConstructor(ParsingInitializer.class, Consumer.class)
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
					.getConstructor(ParsingInitializer.class, Consumer.class)
					.newInstance(this, putReference);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return (T) insertionModeCache.get(modeClass);
	}

	public ParserSettings getSettings() {
		return this.settings;
	}
	
}
