package everyos.web.spec.css.parser.selectors;

import everyos.web.spec.css.parser.ParseFormatException;
import everyos.web.spec.css.parser.TokenStream;
import everyos.web.spec.css.selectors.ComplexSelectorPart;

public interface SelectorParser {

	ComplexSelectorPart parse(TokenStream stream) throws ParseFormatException;
	
}
