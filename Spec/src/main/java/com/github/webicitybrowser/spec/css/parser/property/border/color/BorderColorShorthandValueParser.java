package com.github.webicitybrowser.spec.css.parser.property.border.color;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.property.border.MultiBorderColorValue;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public class BorderColorShorthandValueParser implements PropertyValueParser<MultiBorderColorValue> {

	private final BorderColorLonghandValueParser longhandParser = new BorderColorLonghandValueParser();

	@Override
	public PropertyValueParseResult<MultiBorderColorValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkSelectorValid(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}

		ColorValue[] values = new ColorValue[4];
		int used = 0;
		for (int i = 0; i < length; i++) {
			PropertyValueParseResult<ColorValue> parseResult = longhandParser.parse(tokens, offset + i, 1);
			if (parseResult.getResult().isEmpty()) {
				break;
			}

			values[i] = parseResult.getResult().get();
			used ++;
		}

		int usedLength = 4;
		if (used == 0) {
			return PropertyValueParseResultImp.empty();
		} else if (used < 4) {
			values = new ColorValue[] {values[0], values[0], values[0], values[0]};
			usedLength = 1;
		}

		return convertValuesToBorderColorValue(values, usedLength);
	}

	private boolean checkSelectorValid(TokenLike[] tokens, int offset, int length) {
		return length > 0;
	}

	private PropertyValueParseResult<MultiBorderColorValue> convertValuesToBorderColorValue(ColorValue[] values, int length) {
		MultiBorderColorValue value = new MultiBorderColorValue(values[0], values[1], values[2], values[3]);
		return PropertyValueParseResultImp.of(value, length);
	}
	
}
