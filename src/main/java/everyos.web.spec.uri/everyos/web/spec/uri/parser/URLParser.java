package everyos.web.spec.uri.parser;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;

import everyos.web.spec.uri.URL;
import everyos.web.spec.uri.builder.URLBuilder;
import everyos.web.spec.uri.parser.state.SchemeStartState;
import everyos.web.spec.uri.parser.state.URLParseState;

public class URLParser {

	public URL parse(
		URL base, String input, URLBuilder url, Class<? extends URLParseState> stateOverride
	) throws MalformedURLException {
		if (url == null) {
			url = new URLBuilder();
			input = sanitizeC0Input(input);
		}
		input = sanitizeTabNewLine(input);
		
		Class<? extends URLParseState> initialStateClass = stateOverride != null ?
			stateOverride :
			SchemeStartState.class;
		
		URLParserContext context = new URLParserContext(stateOverride, input, url, base);
		
		boolean atSignSeen = false;
		boolean insideBrackets = false;
		boolean passwordTokenSeen = false;
		
		URLParseState state = context.getParseState(initialStateClass);
		while (true) {
			int ch = context.read();
			state = state.parse(ch);
			if (ch == -1) {
				break;
			}
		}
		
		return null;
	}

	private String sanitizeC0Input(String input) {
		// TODO Implement method
		return input;
	}
	
	private String sanitizeTabNewLine(String input) {
		String sanitized = input.replaceAll("(\n\t)", "");
		if (!sanitized.equals(input)) {
			validationError();
		}
		
		return sanitized;
	}

	private int read(Reader reader) {
		try {
			return reader.read();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void validationError() {
		// TODO Auto-generated method stub
		
	}

}
