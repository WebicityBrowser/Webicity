package com.github.webicitybrowser.spec.css.parser.property.flex;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexDirectionValue;

public class FlexDirectionValueParser implements PropertyValueParser<FlexDirectionValue> {

	@Override
	public PropertyValueParseResult<FlexDirectionValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkValueFormat(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}

		return switch (((IdentToken) tokens[offset]).getValue()) {
			case "row" -> PropertyValueParseResultImp.of(FlexDirectionValue.ROW, 1);
			case "row-reverse" -> PropertyValueParseResultImp.of(FlexDirectionValue.ROW_REVERSE, 1);
			case "column" -> PropertyValueParseResultImp.of(FlexDirectionValue.COLUMN, 1);
			case "column-reverse" -> PropertyValueParseResultImp.of(FlexDirectionValue.COLUMN_REVERSE, 1);
			default -> PropertyValueParseResultImp.empty();
		};
	}

	private boolean checkValueFormat(TokenLike[] tokens, int offset, int length) {
		return length == 1 && tokens[offset] instanceof IdentToken;
	}
	
}
