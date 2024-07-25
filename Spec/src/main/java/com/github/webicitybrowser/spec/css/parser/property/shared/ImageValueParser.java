package com.github.webicitybrowser.spec.css.parser.property.shared;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;

public class ImageValueParser implements PropertyValueParser<CSSValue> {

	private final URLValueParser urlValueParser = new URLValueParser();

	@Override
	public PropertyValueParseResult<CSSValue> parse(TokenLike[] tokens, int offset, int length) {
		// TODO: Support gradient
		return urlValueParser.parse(tokens, offset, length).as(CSSValue.class);
	}
	
}
