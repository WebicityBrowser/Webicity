package com.github.webicitybrowser.spec.css.parser.property.flex;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexJustifyContentValue;

public class FlexJustifyContentValueParser implements PropertyValueParser<FlexJustifyContentValue> {

	@Override
	public PropertyValueParseResult<FlexJustifyContentValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkValueFormat(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}

		String value = ((IdentToken) tokens[offset]).getValue();
		return switch (value) {
			case "center" -> PropertyValueParseResultImp.of(FlexJustifyContentValue.CENTER, 1);
			case "flex-end" -> PropertyValueParseResultImp.of(FlexJustifyContentValue.FLEX_END, 1);
			case "flex-start" -> PropertyValueParseResultImp.of(FlexJustifyContentValue.FLEX_START, 1);
			case "space-around" -> PropertyValueParseResultImp.of(FlexJustifyContentValue.SPACE_AROUND, 1);
			case "space-between" -> PropertyValueParseResultImp.of(FlexJustifyContentValue.SPACE_BETWEEN, 1);
			default -> PropertyValueParseResultImp.empty();
		};
	}

	private boolean checkValueFormat(TokenLike[] tokens, int offset, int length) {
		return length == 1 && tokens[offset] instanceof IdentToken;
	}

}
