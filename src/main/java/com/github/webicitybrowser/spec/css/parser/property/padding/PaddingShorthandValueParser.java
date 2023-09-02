package com.github.webicitybrowser.spec.css.parser.property.padding;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.padding.PaddingValue;

public class PaddingShorthandValueParser implements PropertyValueParser<PaddingValue> {

	private final PaddingLonghandValueParser longhandParser = new PaddingLonghandValueParser();
	
	@Override
	public PropertyValueParseResult<PaddingValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkSelectorValid(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}

		CSSValue[] values = new CSSValue[length];
		for (int i = 0; i < length; i++) {
			PropertyValueParseResult<CSSValue> parseResult = longhandParser.parse(tokens, offset + i, 1);
			if (parseResult.getResult().isEmpty()) {
				return PropertyValueParseResultImp.empty();
			}

			values[i] = parseResult.getResult().get();
		}

		return convertValuesToPaddingValue(values);
	}

	private PropertyValueParseResult<PaddingValue> convertValuesToPaddingValue(CSSValue[] values) {
		switch (values.length) {
			case 1 -> {
				CSSValue value = values[0];
				return PropertyValueParseResultImp.of(new PaddingValue(value, value, value, value), 1);
			}
			case 2 -> {
				CSSValue top = values[0];
				CSSValue bottom = values[0];
				CSSValue left = values[1];
				CSSValue right = values[1];
				return PropertyValueParseResultImp.of(new PaddingValue(left, right, top, bottom), 2);
			}
			case 3 -> {
				CSSValue top = values[0];
				CSSValue left = values[1];
				CSSValue right = values[1];
				CSSValue bottom = values[2];
				return PropertyValueParseResultImp.of(new PaddingValue(left, right, top, bottom), 3);
			}
			case 4 -> {
				CSSValue top = values[0];
				CSSValue right = values[1];
				CSSValue bottom = values[2];
				CSSValue left = values[3];
				return PropertyValueParseResultImp.of(new PaddingValue(left, right, top, bottom), 4);
			}
			default -> throw new IllegalStateException("Unexpected value: " + values.length);
		}
	}

	private boolean checkSelectorValid(TokenLike[] tokens, int offset, int length) {
		return length >= 1 && length <= 4;
	}
	
}