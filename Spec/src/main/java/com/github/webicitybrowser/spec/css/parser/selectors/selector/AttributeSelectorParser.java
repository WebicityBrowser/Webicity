package com.github.webicitybrowser.spec.css.parser.selectors.selector;

import com.github.webicitybrowser.spec.css.QualifiedName;
import com.github.webicitybrowser.spec.css.parser.ParseFormatException;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.selectors.SelectorParser;
import com.github.webicitybrowser.spec.css.parser.selectors.misc.QualifiedNameParser;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.LSBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RSBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.StringToken;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector.AttributeSelectorOperation;

public class AttributeSelectorParser implements SelectorParser {

	private final QualifiedNameParser qualifiedNameParser = new QualifiedNameParser();
	
	@Override
	public AttributeSelector parse(TokenStream stream) throws ParseFormatException {
		if (!(stream.read() instanceof LSBracketToken)) {
			fail(stream);
		}
		
		QualifiedName attribName = qualifiedNameParser.parse(stream);
		
		AttributeSelectorOperation operation = AttributeSelectorOperation.PRESENT;
		String opParameter = "";
		
		if (!(stream.peek() instanceof RSBracketToken)) {
			operation = parseOperation(stream);
			parseEquals(stream);
			opParameter = parseAttribValue(stream);
		}
		
		if (!(stream.read() instanceof RSBracketToken)) {
			fail(stream);
		}
		
		return createAttributeSelector(attribName, operation, opParameter);
	}
	
	private AttributeSelectorOperation parseOperation(TokenStream stream) throws ParseFormatException {
		TokenLike token = stream.read();
		if (!(token instanceof DelimToken)) {
			fail(stream);
		}
		
		switch(((DelimToken) token).getValue()) {
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
		case '=':
			stream.unread();
			return AttributeSelectorOperation.EQUALS;
		default:
			fail(stream);
			return null;
		}
	}
	
	private void parseEquals(TokenStream stream) throws ParseFormatException {
		if (!isDelimToken(stream.read(), '=')) {
			fail(stream);
		}
	}
	
	private String parseAttribValue(TokenStream stream) throws ParseFormatException {
		TokenLike token = stream.read();
		if (token instanceof IdentToken identToken) {
			return identToken.getValue();
		} else if (token instanceof StringToken stringToken) {
			return stringToken.getValue();
		} else {
			fail(stream);
			return null;
		}
	}

	private boolean isDelimToken(TokenLike token, int ch) {
		return
			token instanceof DelimToken &&
			((DelimToken) token).getValue() == ch;
	}
	
	private void fail(TokenStream stream) throws ParseFormatException {
		throw new ParseFormatException("Invalid attribute selector format", stream.position());
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
