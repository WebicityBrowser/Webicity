package com.github.webicitybrowser.spec.css.parser.selectors.selector;

import com.github.webicitybrowser.spec.css.QualifiedName;
import com.github.webicitybrowser.spec.css.parser.ParseFormatException;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.selectors.SelectorParser;
import com.github.webicitybrowser.spec.css.parser.selectors.misc.QualifiedNameParser;
import com.github.webicitybrowser.spec.css.selectors.selector.TypeSelector;

public class TypeSelectorParser implements SelectorParser {
	
	private final QualifiedNameParser qualifiedNameParser = new QualifiedNameParser();

	@Override
	public TypeSelector parse(TokenStream stream) throws ParseFormatException {
		QualifiedName qualifiedName = qualifiedNameParser.parse(stream);
		return createTypeSelector(qualifiedName);
	}
	
	private TypeSelector createTypeSelector(QualifiedName qualifiedName) {
		return new TypeSelector() {	
			@Override
			public QualifiedName getQualifiedName() {
				return qualifiedName;
			}
		};
	}

}
