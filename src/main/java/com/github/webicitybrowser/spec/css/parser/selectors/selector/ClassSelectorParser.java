package com.github.webicitybrowser.spec.css.parser.selectors.selector;

import com.github.webicitybrowser.spec.css.QualifiedName;
import com.github.webicitybrowser.spec.css.parser.ParseFormatException;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.selectors.SelectorParser;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector;

public class ClassSelectorParser implements SelectorParser {

	@Override
	public AttributeSelector parse(TokenStream stream) throws ParseFormatException {
		if (!(
			stream.read() instanceof DelimToken delimToken &&
			delimToken.getValue() == '.'
		)) {
			fail(stream);
		}
		
		TokenLike token = stream.read();
		if (!(token instanceof IdentToken)) {
			fail(stream);
		}
		String ident = ((IdentToken) token).getValue();
		
		return createClassSelector(ident);
	}
	
	private void fail(TokenStream stream) throws ParseFormatException {
		throw new ParseFormatException("Invalid class selector format", stream.position());
	}
	
	private AttributeSelector createClassSelector(String className) {
		return new AttributeSelector() {
			@Override
			public AttributeSelectorOperation getOperation() {
				return AttributeSelectorOperation.ONE_OF;
			}
			
			@Override
			public String getComparisonValue() {
				return className;
			}
			
			@Override
			public QualifiedName getAttributeName() {
				return createQualifiedName();
			}
		};
	}
	
	private QualifiedName createQualifiedName() {
		return new QualifiedName() {	
			@Override
			public String getNamespace() {
				return QualifiedName.DEFAULT_NAMESPACE;
			}
			
			@Override
			public String getName() {
				return "class";
			}
		};
	}

}
