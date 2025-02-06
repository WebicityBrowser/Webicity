package com.github.webicitybrowser.spec.css.parser.selectors.selector;

import com.github.webicitybrowser.spec.css.QualifiedName;
import com.github.webicitybrowser.spec.css.parser.ParseFormatException;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.selectors.SelectorParser;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector.AttributeSelectorOperation;

public class ClassSelectorParser implements SelectorParser {

	private static final QualifiedName CLASS_NAME = QualifiedName.create(QualifiedName.DEFAULT_NAMESPACE, "class");

	@Override
	public AttributeSelector parse(TokenStream stream) throws ParseFormatException {
		if (!(
			stream.read() instanceof DelimToken delimToken &&
			delimToken.value() == '.'
		)) {
			fail(stream);
		}
		
		TokenLike token = stream.read();
		if (!(token instanceof IdentToken)) {
			fail(stream);
		}
		String ident = ((IdentToken) token).value();
		
		return new AttributeSelector(CLASS_NAME, AttributeSelectorOperation.ONE_OF, ident);
	}
	
	private void fail(TokenStream stream) throws ParseFormatException {
		throw new ParseFormatException("Invalid class selector format", stream.position());
	}

}
