package com.github.webicitybrowser.spec.css.parser.property.position;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.threadyweb.graphical.value.PositionTypeValue;

public class PositionTypeValueParser implements PropertyValueParser<PositionTypeValue> {

	@Override
	public PropertyValueParseResult<PositionTypeValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkValueFormat(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}

		return switch (((IdentToken) tokens[offset]).getValue()) {
			case "static" -> PropertyValueParseResultImp.of(PositionTypeValue.STATIC, 1);
			case "relative" -> PropertyValueParseResultImp.of(PositionTypeValue.RELATIVE, 1);
			case "sticky" -> PropertyValueParseResultImp.of(PositionTypeValue.STICKY, 1);
			case "absolute" -> PropertyValueParseResultImp.of(PositionTypeValue.ABSOLUTE, 1);
			case "fixed" -> PropertyValueParseResultImp.of(PositionTypeValue.FIXED, 1);
			default -> PropertyValueParseResultImp.empty();
		};
	}
	
	private boolean checkValueFormat(TokenLike[] tokens, int offset, int length) {
		return length > 0 && tokens[offset] instanceof IdentToken;
	}
	
}
