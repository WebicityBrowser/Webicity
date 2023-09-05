package com.github.webicitybrowser.spec.css.parser.property.flex;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexWrapValue;

public class FlexWrapValueParser implements PropertyValueParser<FlexWrapValue> {

	@Override
	public PropertyValueParseResult<FlexWrapValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkValueFormat(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}

		return switch (((IdentToken) tokens[offset]).getValue()) {
			case "nowrap" -> PropertyValueParseResultImp.of(FlexWrapValue.NOWRAP, 1);
			case "wrap" -> PropertyValueParseResultImp.of(FlexWrapValue.WRAP, 1);
			case "wrap-reverse" -> PropertyValueParseResultImp.of(FlexWrapValue.WRAP_REVERSE, 1);
			default -> PropertyValueParseResultImp.empty();
		};
	}
	
	private boolean checkValueFormat(TokenLike[] tokens, int offset, int length) {
		return length == 1 && tokens[offset] instanceof IdentToken;
	}

}
