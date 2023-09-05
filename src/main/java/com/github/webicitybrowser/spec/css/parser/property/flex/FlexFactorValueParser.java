package com.github.webicitybrowser.spec.css.parser.property.flex;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberToken;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexFactorValue;

public class FlexFactorValueParser implements PropertyValueParser<FlexFactorValue> {

	@Override
	public PropertyValueParseResult<FlexFactorValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkValueFormat(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}

		float flexFactor = ((NumberToken) tokens[offset]).getValue().floatValue();
		return PropertyValueParseResultImp.of(new FlexFactorValue(flexFactor), 1);
	}

	private boolean checkValueFormat(TokenLike[] tokens, int offset, int length) {
		return length == 1 && tokens[offset] instanceof NumberToken;
	}
	
}
