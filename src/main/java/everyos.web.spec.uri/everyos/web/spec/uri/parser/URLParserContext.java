package everyos.web.spec.uri.parser;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import everyos.web.spec.uri.URL;
import everyos.web.spec.uri.builder.URLBuilder;
import everyos.web.spec.uri.parser.state.URLParseState;

public class URLParserContext {
	
	private final Class<? extends URLParseState> stateOverride;
	private final String input;
	private final URLBuilder urlBuilder;
	private final URL base;
	
	private final Map<Class<? extends URLParseState>, URLParseState> tokenizeStateCache = new HashMap<>();
	private final StringBuilder buffer = new StringBuilder();
	
	private PushbackReader reader;
	
	public URLParserContext(
		Class<? extends URLParseState> initialStateClass, String input,
		URLBuilder urlBuilder, URL base
	) {
		this.stateOverride = initialStateClass;
		this.input = input;
		this.urlBuilder = urlBuilder;
		this.base = base;
		
		restart();
	}

	@SuppressWarnings("unchecked")
	public <T extends URLParseState> T getParseState(Class<T> stateClass) {
		if (!tokenizeStateCache.containsKey(stateClass)) {
			try {
				Consumer<URLParseState> putReference = state -> tokenizeStateCache.put(stateClass, state);
				stateClass
					.getConstructor(URLParserContext.class, Consumer.class)
					.newInstance(this, putReference);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return (T) tokenizeStateCache.get(stateClass);
	}
	
	public int read() {
		try {
			return reader.read();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void unread(int ch) {
		try {
			reader.unread(ch);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	// TODO: What happens if bytes left too short?
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
					reader.read();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void restart() {
		this.reader = new PushbackReader(new StringReader(input));
	}
	
	public void recordValidationError() {}
	
	public URLBuilder getURLBuilder() {
		return this.urlBuilder;
	};
	
	public Class<? extends URLParseState> getStateOverride() {
		return this.stateOverride;
	}

	public StringBuilder getBuffer() {
		return buffer;
	}

	public URL getBase() {
		return base;
	}
	
}
