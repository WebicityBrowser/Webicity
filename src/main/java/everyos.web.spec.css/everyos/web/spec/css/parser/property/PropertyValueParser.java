package everyos.web.spec.css.parser.property;

import everyos.web.spec.css.parser.TokenLike;

public interface PropertyValueParser<T> {

	PropertyValueParseResult<T> parse(TokenLike[] tokens, int offset, int length);
	
}
