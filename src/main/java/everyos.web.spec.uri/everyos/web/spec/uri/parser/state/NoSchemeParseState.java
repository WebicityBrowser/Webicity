package everyos.web.spec.uri.parser.state;

import java.net.MalformedURLException;
import java.util.function.Consumer;

import everyos.web.spec.uri.URL;
import everyos.web.spec.uri.parser.URLParserContext;

public class NoSchemeParseState implements URLParseState {

	private final URLParserContext context;

	public NoSchemeParseState(URLParserContext context, Consumer<URLParseState> callback) {
		callback.accept(this);
		this.context = context;
	}
	
	@Override
	public URLParseState parse(int ch) throws MalformedURLException {
		URL base = context.getBase();
		
		return null;
	}

}
