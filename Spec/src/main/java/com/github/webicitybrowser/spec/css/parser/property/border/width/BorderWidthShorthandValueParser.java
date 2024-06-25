package com.github.webicitybrowser.spec.css.parser.property.border.width;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.border.MultiBorderWidthValue;

public class BorderWidthShorthandValueParser implements PropertyValueParser<MultiBorderWidthValue> {
	
	private final BorderWidthLonghandValueParser longhandParser = new BorderWidthLonghandValueParser();

	@Override
	public PropertyValueParseResult<MultiBorderWidthValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkSelectorValid(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}

		CSSValue[] values = new CSSValue[4];
		int used = 0;
		for (int i = 0; i < length; i++) {
			PropertyValueParseResult<CSSValue> parseResult = longhandParser.parse(tokens, offset + i, 1);
			if (parseResult.getResult().isEmpty()) break;

			values[i] = parseResult.getResult().get();
			used++;
		}

		int usedLength = 4;
		if (used == 0) {
			return PropertyValueParseResultImp.empty();
		}
		if (used < 4) {
			values = new CSSValue[] { values[0], values[0], values[0], values[0] };
			usedLength = 1;
		}

		return convertValuesToBorderWidthValue(values, usedLength);
	}

	private boolean checkSelectorValid(TokenLike[] tokens, int offset, int length) {
		return length == 1 || length == 4;
	}

	private PropertyValueParseResult<MultiBorderWidthValue> convertValuesToBorderWidthValue(CSSValue[] values, int length) {
		MultiBorderWidthValue value = new MultiBorderWidthValue(values[3], values[1], values[0], values[2]);
		return PropertyValueParseResultImp.of(value, length);
	}

}
