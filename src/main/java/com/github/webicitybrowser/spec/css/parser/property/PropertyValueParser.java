package com.github.webicitybrowser.spec.css.parser.property;

import com.github.webicitybrowser.spec.css.parser.TokenLike;

public interface PropertyValueParser<T> {

	PropertyValueParseResult<T> parse(TokenLike[] tokens, int offset, int length);
	
}
