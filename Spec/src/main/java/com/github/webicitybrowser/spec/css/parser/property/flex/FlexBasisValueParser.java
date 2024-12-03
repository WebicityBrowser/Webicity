package com.github.webicitybrowser.spec.css.parser.property.flex;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.property.shared.length.LengthValueParser;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.shared.basic.AutoValue;

public class FlexBasisValueParser implements PropertyValueParser<CSSValue> {

	private final LengthValueParser lengthValueParser = new LengthValueParser();

	@Override
	public PropertyValueParseResult<CSSValue> parse(TokenLike[] tokens, int offset, int length) {
		if (offset > 0 && tokens[offset]  instanceof IdentToken identToken && identToken.getValue().equals("content")) {
			return PropertyValueParseResultImp.of(new AutoValue(), 1);
		}

		return lengthValueParser.parse(tokens, offset, length);
	}
	
}
