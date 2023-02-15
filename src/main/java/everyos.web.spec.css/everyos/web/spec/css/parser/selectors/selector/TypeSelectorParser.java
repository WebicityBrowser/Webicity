package everyos.web.spec.css.parser.selectors.selector;

import everyos.web.spec.css.QualifiedName;
import everyos.web.spec.css.parser.ParseFormatException;
import everyos.web.spec.css.parser.TokenStream;
import everyos.web.spec.css.parser.selectors.SelectorParser;
import everyos.web.spec.css.parser.selectors.misc.QualifiedNameParser;
import everyos.web.spec.css.selectors.selector.TypeSelector;

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
