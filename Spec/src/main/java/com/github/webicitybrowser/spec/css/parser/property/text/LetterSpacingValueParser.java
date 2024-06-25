package com.github.webicitybrowser.spec.css.parser.property.text;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.property.shared.length.LengthValueParser;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.shared.length.AbsoluteLengthValue;
import com.github.webicitybrowser.spec.css.property.shared.length.LengthValue;

public class LetterSpacingValueParser implements PropertyValueParser<LengthValue> {

	private final LengthValueParser lengthValueParser = new LengthValueParser();

	@Override
	public PropertyValueParseResult<LengthValue> parse(TokenLike[] tokens, int offset, int length) {
		if (length > 0 && tokens[offset] instanceof IdentToken && ((IdentToken) tokens[offset]).equals("normal")) {
			return PropertyValueParseResultImp.of(AbsoluteLengthValue.of(0, "px"), 1);
		}

		return lengthValueParser.parse(tokens, offset, length);
	}
	
}
