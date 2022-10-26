package everyos.web.spec.css.parser.selectors.selector;

import everyos.web.spec.css.parser.ParseFormatException;
import everyos.web.spec.css.parser.selectors.SelectorParser;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.selectors.selector.SimpleSelector;
import everyos.web.spec.css.selectors.selector.TypeSelector;

public class TypeSelectorParser implements SelectorParser {

	private final ElementNameSelectorParser elementNameSelectorParser = new ElementNameSelectorParser();
	
	@Override
	public TypeSelector parse(Token[] tokens, int offset, int length) throws ParseFormatException {
		checkSelectorFormat(tokens, offset, length);
		
		SimpleSelector innerSelector = parseInnerSelector(tokens, offset, length);
		return createTypeSelector(TypeSelector.DEFAULT_NAMESPACE, innerSelector);
	}

	private void checkSelectorFormat(Token[] tokens, int offset, int length) throws ParseFormatException {
		if (length == 0) {
			throw new ParseFormatException("Invalid namespace selector format", offset);
		}
	}
	
	private SimpleSelector parseInnerSelector(Token[] tokens, int offset, int length) throws ParseFormatException {
		return elementNameSelectorParser.parse(tokens, offset, length);
	}
	
	private TypeSelector createTypeSelector(String namespace, SimpleSelector childSelector) {
		return new TypeSelector() {

			@Override
			public String getNamespace() {
				return namespace;
			}

			@Override
			public SimpleSelector getInnerSelector() {
				return childSelector;
			}
			
		};
	}

}
