package everyos.web.spec.css.parser.selectors.selector;

import everyos.web.spec.css.parser.ParseFormatException;
import everyos.web.spec.css.parser.selectors.SelectorParser;
import everyos.web.spec.css.parser.tokens.IdentToken;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.selectors.selector.ElementNameSelector;

public class ElementNameSelectorParser implements SelectorParser {

	@Override
	public ElementNameSelector parse(Token[] tokens, int offset, int length) throws ParseFormatException {
		checkSelectorFormat(tokens, offset, length);
		
		String elementName = ((IdentToken) tokens[offset]).getValue();
		return createElementNameSelector(elementName);
	}

	private void checkSelectorFormat(Token[] tokens, int offset, int length) throws ParseFormatException {
		if (length != 1 || tokens[offset] == null || !(tokens[offset] instanceof IdentToken)) {
			throw new ParseFormatException("Invalid element name selector format", length);
		}
	}
	
	private ElementNameSelector createElementNameSelector(String elementName) {
		return () -> elementName;
	}

}
