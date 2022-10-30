package everyos.web.spec.css.parser.selectors.selector;

import everyos.web.spec.css.QualifiedName;
import everyos.web.spec.css.parser.ParseFormatException;
import everyos.web.spec.css.parser.selectors.SelectorParser;
import everyos.web.spec.css.parser.selectors.misc.QualifiedNameParser;
import everyos.web.spec.css.parser.tokens.DelimToken;
import everyos.web.spec.css.parser.tokens.IdentToken;
import everyos.web.spec.css.parser.tokens.LSBracketToken;
import everyos.web.spec.css.parser.tokens.RSBracketToken;
import everyos.web.spec.css.parser.tokens.StringToken;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.selectors.selector.AttributeSelector;
import everyos.web.spec.css.selectors.selector.AttributeSelector.AttributeSelectorOperation;

public class AttributeSelectorParser implements SelectorParser {

	private final QualifiedNameParser qualifiedNameParser = new QualifiedNameParser();
	
	@Override
	public AttributeSelector parse(Token[] tokens, int offset, int length) throws ParseFormatException {
		checkSelectorFormat(tokens, offset, length);
		
		int attrNameLength = getAttrNameLength(tokens, offset + 1, length - 2);
		QualifiedName attrName = qualifiedNameParser.parse(tokens, offset + 1, attrNameLength);
		
		int remainingParseOffset = offset + 1 + attrNameLength;
		switch(length - 2 - attrNameLength) {
		case 0:
			return parseAttributePresentSelector(attrName, tokens, remainingParseOffset);
		case 2:
			return parseAttributeEqualsSelector(attrName, tokens, remainingParseOffset);
		case 3:
			return parseAttributeComparisonSelector(attrName, tokens, remainingParseOffset);
		default:
			throw new ParseFormatException("Invalid attribute selector format", offset);
		}
	}

	private int getAttrNameLength(Token[] tokens, int offset, int length) {
		int totalLength = 0;
		for (int i = 0; i < length && !isEqualsToken(tokens, offset + i); i++) {
			totalLength++;
		}
		int attrNameEnd = offset + totalLength;
		if (
			!(tokens[totalLength] instanceof IdentToken) &&
			!(isCharToken(tokens, attrNameEnd, offset, '*') && isCharToken(tokens, attrNameEnd - 1, offset, '|'))
		) {
			totalLength--;
		}
		
		System.out.println(totalLength);
		return totalLength;
	}

	private AttributeSelector parseAttributePresentSelector(QualifiedName attrName, Token[] tokens, int offset) throws ParseFormatException {
		return createAttributeSelector(attrName, AttributeSelectorOperation.ONE_OF, "");
	}
	
	private AttributeSelector parseAttributeEqualsSelector(QualifiedName attrName, Token[] tokens, int offset) throws ParseFormatException {
		ensureEqualsToken(tokens, offset);
		String attrVal = parseAttrVal(tokens, offset + 1);
		return createAttributeSelector(attrName, AttributeSelectorOperation.EQUALS, attrVal);
	}
	
	private AttributeSelector parseAttributeComparisonSelector(QualifiedName attrName, Token[] tokens, int offset) throws ParseFormatException {
		AttributeSelectorOperation operation = parseOperation(tokens, offset);
		ensureEqualsToken(tokens, offset + 1);
		String attrVal = parseAttrVal(tokens, offset + 2);
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
			return AttributeSelectorOperation.ONE_OF;
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
	
	private boolean isCharToken(Token[] tokens, int offset, int minOffset, char ch) {
		return
			offset >= minOffset &&
			tokens[offset] instanceof DelimToken &&
			((DelimToken) tokens[offset]).getValue() == ch;
	}

	private void checkSelectorFormat(Token[] tokens, int offset, int length) throws ParseFormatException {
		if (
			length < 3 ||
			!(tokens[offset] instanceof LSBracketToken) ||
			!(tokens[offset + length - 1] instanceof RSBracketToken)
		) {
			throw new ParseFormatException("Invalid attribute selector format", offset);
		}
	}
	
	private AttributeSelector createAttributeSelector(QualifiedName attrName, AttributeSelectorOperation operation, String comparison) {
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
			public QualifiedName getAttributeName() {
				return attrName;
			}
		};
	}

}
