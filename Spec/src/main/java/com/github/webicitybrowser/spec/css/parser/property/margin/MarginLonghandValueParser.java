package com.github.webicitybrowser.spec.css.parser.property.margin;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.property.shared.lengthpercentage.LengthPercentageValueParser;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.shared.basic.AutoValue;

public class MarginLonghandValueParser implements PropertyValueParser<CSSValue> {

	private final LengthPercentageValueParser lengthPercentageValueParser = new LengthPercentageValueParser();

	@Override
	public PropertyValueParseResult<CSSValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkSelectorFormat(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}

		if (tokens[offset] instanceof IdentToken identToken && identToken.getValue().equals("auto")) {
			return PropertyValueParseResultImp.of(new AutoValue() {}, 1);
		}

		return lengthPercentageValueParser.parse(tokens, offset, 1);
	}

	private boolean checkSelectorFormat(TokenLike[] tokens, int offset, int length) {
		return length > 0 && offset < tokens.length;
	}
	
}
