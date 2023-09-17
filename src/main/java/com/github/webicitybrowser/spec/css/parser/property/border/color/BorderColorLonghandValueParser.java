package com.github.webicitybrowser.spec.css.parser.property.border.color;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.color.ColorValueParser;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public class BorderColorLonghandValueParser implements PropertyValueParser<ColorValue> {

	private final ColorValueParser colorParser = new ColorValueParser();

	@Override
	public PropertyValueParseResult<ColorValue> parse(TokenLike[] tokens, int offset, int length) {
		return colorParser.parse(tokens, offset, length);
	}
	
}
