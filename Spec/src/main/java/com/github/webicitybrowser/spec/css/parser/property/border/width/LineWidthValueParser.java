package com.github.webicitybrowser.spec.css.parser.property.border.width;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.shared.lengthpercentage.LengthPercentageValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;

public class LineWidthValueParser implements PropertyValueParser<CSSValue> {

	private final LengthPercentageValueParser lengthPercentageValueParser = new LengthPercentageValueParser();

	@Override
	public PropertyValueParseResult<CSSValue> parse(TokenLike[] tokens, int offset, int length) {
		return lengthPercentageValueParser.parse(tokens, offset, length);
	}
	
}
