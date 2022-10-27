package everyos.web.spec.css.parser.selectors.selector;

import everyos.web.spec.css.parser.ParseFormatException;
import everyos.web.spec.css.parser.selectors.SelectorParser;
import everyos.web.spec.css.parser.tokens.DelimToken;
import everyos.web.spec.css.parser.tokens.IdentToken;
import everyos.web.spec.css.parser.tokens.LSBrackToken;
import everyos.web.spec.css.parser.tokens.RSBrackToken;
import everyos.web.spec.css.parser.tokens.StringToken;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.selectors.selector.AttributeSelector;
import everyos.web.spec.css.selectors.selector.AttributeSelector.AttributeSelectorOperation;

public class AttributeSelectorParser implements SelectorParser {

	@Override
	public AttributeSelector parse(Token[] tokens, int offset, int length) throws ParseFormatException {
		checkSelectorFormat(tokens, offset, length);
		
		switch(length) {
		case 3:
			return parseAttributePresentSelector(tokens, offset + 1);
		case 5:
			return parseAttributeEqualsSelector(tokens, offset + 1);
		case 6:
			return parseAttributeComparisonSelector(tokens, offset + 1);
		default:
			throw new ParseFormatException("Invalid attribute selector format", offset);
		}
	}

	private AttributeSelector parseAttributePresentSelector(Token[] tokens, int offset) throws ParseFormatException {
		ensureIdentToken(tokens, offset);
		String attrName = ((IdentToken) tokens[offset]).getValue();
		return createAttributeSelector(attrName, AttributeSelectorOperation.NOT_EQUALS, "");
	}
	
	private AttributeSelector parseAttributeEqualsSelector(Token[] tokens, int offset) throws ParseFormatException {
		ensureIdentToken(tokens, offset);
		String attrName = ((IdentToken) tokens[offset]).getValue();
		ensureEqualsToken(tokens, offset + 1);
		String attrVal = parseAttrVal(tokens, offset + 2);
		return createAttributeSelector(attrName, AttributeSelectorOperation.EQUALS, attrVal);
	}
	
	private AttributeSelector parseAttributeComparisonSelector(Token[] tokens, int offset) throws ParseFormatException {
		ensureIdentToken(tokens, offset);
		String attrName = ((IdentToken) tokens[offset]).getValue();
		AttributeSelectorOperation operation = parseOperation(tokens, offset + 1);
		ensureEqualsToken(tokens, offset + 2);
		String attrVal = parseAttrVal(tokens, offset + 3);
		return createAttributeSelector(attrName, operation, attrVal);
	}

	private String parseAttrVal(Token[] tokens, int offset) throws ParseFormatException {
		Token token = tokens[offset];
		if (token instanceof IdentToken) {
			return ((IdentToken) token).getValue();
		} else if (token instanceof StringToken) {
			return ((StringToken) token).getValue();
		} else {
			throw new ParseFormatException("Invalid attribute selector format", offset);
		}
	}
	
	private AttributeSelectorOperation parseOperation(Token[] tokens, int offset) throws ParseFormatException {
		if (!(tokens[offset] instanceof DelimToken)) {
			throw new ParseFormatException("Invalid attribute selector format", offset);
		}
		switch(((DelimToken) tokens[offset]).getValue()) {
		case '~':
			return AttributeSelectorOperation.NOT_EQUALS;
		case '|':
			return AttributeSelectorOperation.HAS_SUBCODE;
		case '^':
			return AttributeSelectorOperation.BEGINS_WITH;
		case '&':
			return AttributeSelectorOperation.ENDS_WITH;
		case '*':
			return AttributeSelectorOperation.CONTAINS;
		default:
			throw new ParseFormatException("Invalid attribute selector format", offset);
		}
	}

	private void ensureIdentToken(Token[] tokens, int offset) throws ParseFormatException {
		if (!(tokens[offset] instanceof IdentToken)) {
			throw new ParseFormatException("Invalid attribute selector format", offset);
		}
	}
	
	private void ensureEqualsToken(Token[] tokens, int offset) throws ParseFormatException {
		if (!isEqualsToken(tokens, offset)) {
			throw new ParseFormatException("Invalid attribute selector format", offset);
		}
	}

	private boolean isEqualsToken(Token[] tokens, int offset) {
		return
			tokens[offset] instanceof DelimToken &&
			((DelimToken) tokens[offset]).getValue() == '=';
	}

	private void checkSelectorFormat(Token[] tokens, int offset, int length) throws ParseFormatException {
		if (
			length < 3 ||
			!(tokens[offset] instanceof LSBrackToken) ||
			!(tokens[offset + length - 1] instanceof RSBrackToken)
		) {
			throw new ParseFormatException("Invalid attribute selector format", offset);
		}
	}
	
	private AttributeSelector createAttributeSelector(String attrName, AttributeSelectorOperation operation, String comparison) {
		return new AttributeSelector() {
			@Override
			public AttributeSelectorOperation getOperation() {
				return operation;
			}
			
			@Override
			public String getComparisonValue() {
				return comparison;
			}
			
			@Override
			public String getAttributeName() {
				return attrName;
			}
		};
	}

}
