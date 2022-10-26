package everyos.web.spec.css.parser.selectors;

import everyos.web.spec.css.parser.ParseFormatException;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.selectors.ComplexSelectorPart;

public interface SelectorParser {

	ComplexSelectorPart parse(Token[] tokens, int offset, int length) throws ParseFormatException;
	
}
