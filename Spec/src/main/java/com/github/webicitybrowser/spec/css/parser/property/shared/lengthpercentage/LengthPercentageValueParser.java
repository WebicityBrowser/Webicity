package com.github.webicitybrowser.spec.css.parser.property.shared.lengthpercentage;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParserUtil;
import com.github.webicitybrowser.spec.css.parser.property.shared.length.LengthValueParser;
import com.github.webicitybrowser.spec.css.parser.property.shared.math.MathValueParser;
import com.github.webicitybrowser.spec.css.parser.property.shared.percentage.PercentageValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;

public class LengthPercentageValueParser implements PropertyValueParser<CSSValue> {

	private static final LengthValueParser lengthValueParser = new LengthValueParser();
	private static final PercentageValueParser percentageValueParser = new PercentageValueParser();

	private final MathValueParser mathValueParser = new MathValueParser(LengthPercentageValueParser::parseInternal);

	@Override
	public PropertyValueParseResult<CSSValue> parse(TokenLike[] tokens, int offset, int length) {
		return mathValueParser.parse(tokens, offset, length);
	}
	
	private static PropertyValueParseResult<CSSValue> parseInternal(TokenLike[] tokens, int offset, int length) {
		return PropertyValueParserUtil.takeLongestResult(
			lengthValueParser.parse(tokens, offset, length).as(CSSValue.class),
			percentageValueParser.parse(tokens, offset, length).as(CSSValue.class));
	}
	
}
