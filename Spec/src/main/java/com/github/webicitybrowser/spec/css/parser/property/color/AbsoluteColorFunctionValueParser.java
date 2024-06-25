package com.github.webicitybrowser.spec.css.parser.property.color;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParserUtil;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public class AbsoluteColorFunctionValueParser implements PropertyValueParser<ColorValue> {

	private final PropertyValueParser<ColorValue> rgbColorValueParser = new RGBColorValueParser();

	@Override
	public PropertyValueParseResult<ColorValue> parse(TokenLike[] tokens, int offset, int length) {
		return PropertyValueParserUtil.takeLongestResult(
			rgbColorValueParser.parse(tokens, offset, length));
	}

}
