package com.github.webicitybrowser.spec.css.parser.property.color;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParserUtil;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public class AbsoluteColorBaseValueParser implements PropertyValueParser<ColorValue> {

	private final PropertyValueParser<ColorValue> hexColorValueParser = new HexColorValueParser();
	private final PropertyValueParser<ColorValue> absoluteColorFunctionValueParser = new AbsoluteColorFunctionValueParser();
	private final PropertyValueParser<ColorValue> namedColorValueParser = new NamedColorValueParser();
	private final PropertyValueParser<ColorValue> transparentColorValueParser = new TransparentColorValueParser();

	@Override
	public PropertyValueParseResult<ColorValue> parse(TokenLike[] tokens, int offset, int length) {
		return PropertyValueParserUtil.takeLongestResult(
			hexColorValueParser.parse(tokens, offset, length),
			absoluteColorFunctionValueParser.parse(tokens, offset, length),
			namedColorValueParser.parse(tokens, offset, length),
			transparentColorValueParser.parse(tokens, offset, length));
	}

}
