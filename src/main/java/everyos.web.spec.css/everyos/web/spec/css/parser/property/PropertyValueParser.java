package everyos.web.spec.css.parser.property;

import everyos.web.spec.css.parser.ParseFormatException;
import everyos.web.spec.css.parser.tokens.Token;

public interface PropertyValueParser<T> {

	PropertyValueParseResult<T> parse(Token[] tokens, int offset, int length) throws ParseFormatException;
	
}
