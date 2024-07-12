package com.github.webicitybrowser.spec.css.parser.property.floatbox;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.floatbox.ClearValue;

public class ClearValueParser implements PropertyValueParser<ClearValue> {

	@Override
	public PropertyValueParseResult<ClearValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkValueFormat(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}

		return switch (((IdentToken) tokens[offset]).getValue()) {
			case "left" -> PropertyValueParseResultImp.of(ClearValue.LEFT, 1);
			case "right" -> PropertyValueParseResultImp.of(ClearValue.RIGHT, 1);
			case "none" -> PropertyValueParseResultImp.of(ClearValue.NONE, 1);
			case "both" -> PropertyValueParseResultImp.of(ClearValue.BOTH, 1);
			default -> PropertyValueParseResultImp.empty();
		};
	}

	private boolean checkValueFormat(TokenLike[] tokens, int offset, int length) {
		return length > 0 && tokens[offset] instanceof IdentToken;
	}
	
}
