package com.github.webicitybrowser.spec.css.parser.property;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.property.CSSValue;

public interface PropertyValueParser<T extends CSSValue> {

	PropertyValueParseResult<T> parse(TokenLike[] tokens, int offset, int length);
	
}
