package everyos.web.spec.css.parser.selectors.selector;

import everyos.web.spec.css.parser.ParseFormatException;
import everyos.web.spec.css.parser.selectors.SelectorParser;
import everyos.web.spec.css.parser.tokens.DelimToken;
import everyos.web.spec.css.parser.tokens.IdentToken;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.selectors.selector.TypeSelector;

public class TypeSelectorParser implements SelectorParser {

	@Override
	public TypeSelector parse(Token[] tokens, int offset, int length) throws ParseFormatException {
		checkSelectorFormat(tokens, offset, length);
		
		switch(length) {
		case 1:
			return parseDefaultTypeSelector(tokens, offset);
		case 2:
			return parseNoTypeSelector(tokens, offset);
		case 3:
			return parseNamespaceSelector(tokens, offset);
		default:
			throw new ParseFormatException("Invalid type selector format", offset);
		}
	}

	private void checkSelectorFormat(Token[] tokens, int offset, int length) throws ParseFormatException {
		if (length == 0) {
			throw new ParseFormatException("Invalid type selector format", offset);
		}
	}
	
	private TypeSelector parseDefaultTypeSelector(Token[] tokens, int offset) throws ParseFormatException {
		String elementName = parseElementName(tokens, offset);
		
		return createTypeSelector(TypeSelector.DEFAULT_NAMESPACE, elementName);
	}
	
	private TypeSelector parseNoTypeSelector(Token[] tokens, int offset) throws ParseFormatException {
		checkIsBarToken(tokens, offset);
		String elementName = parseElementName(tokens, offset + 1);
		
		return createTypeSelector(TypeSelector.NO_NAMESPACE, elementName);
	}
	
	private TypeSelector parseNamespaceSelector(Token[] tokens, int offset) throws ParseFormatException {
		if (tokens[offset] instanceof IdentToken) {
			return parseNamedNamespaceSelector(tokens, offset);
		} else if (isGlobToken(tokens, offset)) {
			return parseAnyNamespaceSelector(tokens, offset);
		} else {
			throw new ParseFormatException("Invalid type selector format", offset);
		}
	}
	
	private TypeSelector parseNamedNamespaceSelector(Token[] tokens, int offset) throws ParseFormatException {
		checkIsBarToken(tokens, offset + 1);
		String namespace = ((IdentToken) tokens[offset]).getValue();
		String elementName = parseElementName(tokens, offset + 2);
		
		return createTypeSelector(namespace, elementName);
	}
	
	private TypeSelector parseAnyNamespaceSelector(Token[] tokens, int offset) throws ParseFormatException {
		checkIsBarToken(tokens, offset + 1);
		String elementName = parseElementName(tokens, offset + 2);
		
		return createTypeSelector(TypeSelector.ANY_NAMESPACE, elementName);
	}

	private void checkIsBarToken(Token[] tokens, int offset) throws ParseFormatException {
		if (!isBarToken(tokens, offset)) {
			throw new ParseFormatException("Invalid type selector format", offset);
		}
	}

	private String parseElementName(Token[] tokens, int offset) throws ParseFormatException {
		if (tokens[offset] instanceof IdentToken) {
			return ((IdentToken) tokens[offset]).getValue();
		} else if (isGlobToken(tokens, offset)) {
			return TypeSelector.ANY_ELEMENT;
		} else {
			throw new ParseFormatException("Invalid type selector format", offset);
		}
	}
	
	private boolean isBarToken(Token[] tokens, int offset) {
		return
			tokens[offset] instanceof DelimToken &&
			((DelimToken) tokens[offset]).getValue() == '|';
	}
	
	private boolean isGlobToken(Token[] tokens, int offset) {
		return
			tokens[offset] instanceof DelimToken &&
			((DelimToken) tokens[offset]).getValue() == '*';
	}
	
	private TypeSelector createTypeSelector(String namespace, String elementName) {
		return new TypeSelector() {

			@Override
			public String getNamespace() {
				return namespace;
			}

			@Override
			public String getElementName() {
				return elementName;
			}
			
		};
	}

}
