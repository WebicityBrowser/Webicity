package everyos.web.spec.uri.parser.state;

import java.net.MalformedURLException;
import java.util.function.Consumer;

import everyos.web.spec.uri.parser.URLParserContext;
import everyos.web.spec.uri.util.CharUtil;

public class SchemeStartState implements URLParseState {
	
	private final URLParserContext context;
	
	private final SchemeParseState schemeParseState;
	private final NoSchemeParseState noSchemeParseState;

	public SchemeStartState(URLParserContext context, Consumer<URLParseState> callback) {
		callback.accept(this);
		this.context = context;
		this.schemeParseState = context.getParseState(SchemeParseState.class);
		this.noSchemeParseState = context.getParseState(NoSchemeParseState.class);
	}

	@Override
	public URLParseState parse(int ch) throws MalformedURLException {
		if (CharUtil.isASCIIAlpha(ch)) {
			context
				.getBuffer()
				.appendCodePoint(CharUtil.toASCIILowerCase(ch));
			return schemeParseState;
		} else if (context.getStateOverride() != null) {
			context.unread(ch);
			return noSchemeParseState;
		} else {
			context.recordValidationError();
			throw new MalformedURLException();
		}
	}

}
