package com.github.webicitybrowser.spec.css.parser.property.border.color;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.property.border.color.BorderColorValue;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public class BorderColorShorthandValueParser implements PropertyValueParser<BorderColorValue> {

	private final BorderColorLonghandValueParser longhandParser = new BorderColorLonghandValueParser();

	@Override
	public PropertyValueParseResult<BorderColorValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkSelectorValid(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}

		ColorValue[] values = new ColorValue[4];
		for (int i = 0; i < length; i++) {
			PropertyValueParseResult<ColorValue> parseResult = longhandParser.parse(tokens, offset + i, 1);
			if (parseResult.getResult().isEmpty()) {
				return PropertyValueParseResultImp.empty();
			}

			values[i] = parseResult.getResult().get();
		}

		for (int i = length; i < 4; i++) {
			values[i] = values[0];
		}

		return convertValuesToBorderColorValue(values);
	}

	private boolean checkSelectorValid(TokenLike[] tokens, int offset, int length) {
		return length == 1 || length == 4;
	}

	private PropertyValueParseResult<BorderColorValue> convertValuesToBorderColorValue(ColorValue[] values) {
		BorderColorValue value = new BorderColorValue(values[0], values[1], values[2], values[3]);
		return PropertyValueParseResultImp.of(value, 4);
	}
	
}
