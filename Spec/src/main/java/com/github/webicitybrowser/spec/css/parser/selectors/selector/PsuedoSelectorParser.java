package com.github.webicitybrowser.spec.css.parser.selectors.selector;

import com.github.webicitybrowser.spec.css.parser.ParseFormatException;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.selectors.SelectorParser;
import com.github.webicitybrowser.spec.css.parser.tokens.ColonToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.selectors.selector.psuedo.PsuedoSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.psuedo.RootSelector;

public class PsuedoSelectorParser implements SelectorParser {

	@Override
	public PsuedoSelector parse(TokenStream stream) throws ParseFormatException {
		if (!(stream.read() instanceof ColonToken)) {
			fail(stream);
		}

		TokenLike token = stream.read();
		if (!(token instanceof IdentToken)) {
			fail(stream);
		}
		String ident = ((IdentToken) token).getValue();

		return switch (ident) {
			case "root" -> new RootSelector() {};
			default -> failType(stream, ident);
		};
	}

	private void fail(TokenStream stream) throws ParseFormatException {
		throw new ParseFormatException("Invalid psuedo selector format", stream.position());
	}

	private PsuedoSelector failType(TokenStream stream, String ident) throws ParseFormatException {
		throw new ParseFormatException("Invalid psuedo selector type: " + ident, stream.position());
	}
	
}
