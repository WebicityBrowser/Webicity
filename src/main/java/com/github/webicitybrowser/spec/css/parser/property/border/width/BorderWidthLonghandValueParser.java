package com.github.webicitybrowser.spec.css.parser.property.border.width;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;

public class BorderWidthLonghandValueParser implements PropertyValueParser<CSSValue> {

	private final LineWidthValueParser lineWidthParser = new LineWidthValueParser();

	@Override
	public PropertyValueParseResult<CSSValue> parse(TokenLike[] tokens, int offset, int length) {
		return lineWidthParser.parse(tokens, offset, length);
	}
	
}
