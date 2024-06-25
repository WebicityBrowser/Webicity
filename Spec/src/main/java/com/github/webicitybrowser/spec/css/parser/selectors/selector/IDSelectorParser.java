package com.github.webicitybrowser.spec.css.parser.selectors.selector;

import com.github.webicitybrowser.spec.css.parser.ParseFormatException;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.selectors.SelectorParser;
import com.github.webicitybrowser.spec.css.parser.tokens.HashToken;
import com.github.webicitybrowser.spec.css.selectors.selector.IDSelector;

public class IDSelectorParser implements SelectorParser {

	@Override
	public IDSelector parse(TokenStream stream) throws ParseFormatException {
		TokenLike token = stream.read();
		if (!(token instanceof HashToken)) {
			fail(stream);
		}
		String ident = ((HashToken) token).getValue();
		
		return () -> ident;
	}
	
	private void fail(TokenStream stream) throws ParseFormatException {
		throw new ParseFormatException("Invalid class selector format", stream.position());
	}
	
}
