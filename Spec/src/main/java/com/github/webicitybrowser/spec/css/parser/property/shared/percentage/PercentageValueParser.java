package com.github.webicitybrowser.spec.css.parser.property.shared.percentage;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.PercentageToken;
import com.github.webicitybrowser.spec.css.property.shared.percentage.PercentageValue;

public class PercentageValueParser implements PropertyValueParser<PercentageValue> {

	@Override
	public PropertyValueParseResult<PercentageValue> parse(TokenLike[] tokens, int offset, int length) {
		if (length == 1 && offset < tokens.length && tokens[offset] instanceof PercentageToken percentageToken) {
			return PropertyValueParseResultImp.of(() -> percentageToken.getValue().floatValue(), 1);
		}
		return PropertyValueParseResultImp.empty();
	}
	
}
