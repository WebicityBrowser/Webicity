package com.github.webicitybrowser.spec.css.parser.property.display;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParserUtil;
import com.github.webicitybrowser.spec.css.property.display.DisplayValue;

public class DisplayPropertyValueParser implements PropertyValueParser<DisplayValue> {
	
	private final PropertyValueParser<DisplayValue> innerOuterDisplayValueParser = new InnerOuterDisplayValueParser();

	@Override
	public PropertyValueParseResult<DisplayValue> parse(TokenLike[] tokens, int offset, int length) {
		return PropertyValueParserUtil.takeLongestResult(
			innerOuterDisplayValueParser.parse(tokens, offset, length));
	}

}
