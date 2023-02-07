package everyos.web.spec.uri.parser.state;

import java.net.MalformedURLException;
import java.util.function.Consumer;

import everyos.web.spec.uri.URL;
import everyos.web.spec.uri.builder.URLBuilder;
import everyos.web.spec.uri.parser.URLParserContext;
import everyos.web.spec.uri.util.CharUtil;
import everyos.web.spec.uri.util.SchemeUtil;
import everyos.web.spec.uri.util.URLBuilderUtil;

public class SchemeParseState implements URLParseState {

	private final URLParserContext context;
	
	private final FileParseState fileParseState;
	private final SpecialRelativeOrAuthorityParseState specialRelativeOrAuthorityParseState;
	private final SpecialAuthoritySlashesParseState specialAuthoritySlashesParseState;
	private final PathOrAuthorityParseState pathOrAuthorityParseState;
	private final OpaquePathParseState opaquePathParseState;
	private final NoSchemeParseState noSchemeParseState;

	public SchemeParseState(URLParserContext context, Consumer<URLParseState> callback) {
		callback.accept(this);
		this.context = context;
		this.fileParseState = context.getParseState(FileParseState.class);
		this.specialRelativeOrAuthorityParseState = context.getParseState(SpecialRelativeOrAuthorityParseState.class);
		this.specialAuthoritySlashesParseState = context.getParseState(SpecialAuthoritySlashesParseState.class);
		this.pathOrAuthorityParseState = context.getParseState(PathOrAuthorityParseState.class);
		this.opaquePathParseState = context.getParseState(OpaquePathParseState.class);
		this.noSchemeParseState = context.getParseState(NoSchemeParseState.class);
	}
	
	@Override
	public URLParseState parse(int ch) throws MalformedURLException {
		StringBuilder buffer = context.getBuffer();
		URL base = context.getBase();
		if (
			CharUtil.isASCIIAlphanumeric(ch) || ch == '+' ||
			ch == '-' || ch == '.'
		) {
			context
				.getBuffer()
				.appendCodePoint(CharUtil.toASCIILowerCase(ch));
			return this;
		} else if (ch == ':') {
			URLBuilder url = context.getURLBuilder();
			if (context.getStateOverride() != null) {
				if (
					SchemeUtil.isSpecialScheme(url.getScheme()) !=
					SchemeUtil.isSpecialScheme(buffer.toString())
				) {
					return this;
				} else if (
					buffer.toString().equals("file") &&
					(URLBuilderUtil.hasCredentials(url) ||
					url.getPort() != -1)
				) {
					return this;
				} else if (
					buffer.toString().equals("file") &&
					url.getHost().isEmpty()
				) {
					return this;
				}
			}
			
			url.setScheme(buffer.toString());
			
			if (context.getStateOverride() != null) {
				if (url.getPort() == SchemeUtil.getDefaultPort(url.getScheme())) {
					url.setPort(-1);
				}
				
				return this;
			}
			
			buffer.setLength(0);
			
			if (url.getScheme().equals("file")) {
				if (!context.lookahead(2).equals("//")) {
					context.recordValidationError();
				}
				
				return fileParseState;
			} else if (
				SchemeUtil.isSpecialScheme(url.getScheme()) &&
				base != null &&
				base.getScheme().equals(url.getScheme())
			) {
				assert SchemeUtil.isSpecialScheme(base.getScheme());
				return specialRelativeOrAuthorityParseState;
			} else if (SchemeUtil.isSpecialScheme(url.getScheme())) {
				return specialAuthoritySlashesParseState;
			} else if (context.lookahead(1).equals("/")) {
				context.eat(1);
				return pathOrAuthorityParseState;
			} else {
				url.setPath("");
				return opaquePathParseState;
			}
		} else if (context.getStateOverride() == null) {
			buffer.setLength(0);
			context.restart();
			return noSchemeParseState;
		} else {
			context.recordValidationError();
			throw new MalformedURLException();
		}
	}

}
