package com.github.webicitybrowser.spec.css.parser.property.text;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParserUtil;
import com.github.webicitybrowser.spec.css.parser.property.shared.lengthpercentage.LengthPercentageValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;

public class FontSizeValueParser implements PropertyValueParser<CSSValue> {

	private final LengthPercentageValueParser lengthPercentageValueParser = new LengthPercentageValueParser();

	@Override
	public PropertyValueParseResult<CSSValue> parse(TokenLike[] tokens, int offset, int length) {
		// TODO Other values
		return PropertyValueParserUtil.takeLongestResult(
			lengthPercentageValueParser.parse(tokens, offset, length));
	}
	
}
