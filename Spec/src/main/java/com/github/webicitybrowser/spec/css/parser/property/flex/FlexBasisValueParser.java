package com.github.webicitybrowser.spec.css.parser.property.flex;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.property.shared.length.LengthValueParser;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexBasisValue;
import com.github.webicitybrowser.spec.css.property.shared.basic.AutoValue;

public class FlexBasisValueParser implements PropertyValueParser<FlexBasisValue> {

	private final LengthValueParser lengthValueParser = new LengthValueParser();

	@Override
	public PropertyValueParseResult<FlexBasisValue> parse(TokenLike[] tokens, int offset, int length) {
		if (offset > 0 && tokens[offset]  instanceof IdentToken identToken && identToken.value().equals("auto")) {
			return PropertyValueParseResultImp.of(new FlexBasisValue(new AutoValue(), true), 1);
		}
		if (offset > 0 && tokens[offset]  instanceof IdentToken identToken && identToken.value().equals("content")) {
			return PropertyValueParseResultImp.of(new FlexBasisValue(new AutoValue(), false), 1);
		}

		PropertyValueParseResult<CSSValue> lengthResult = lengthValueParser.parse(tokens, offset, length);
		if (lengthResult.getResult().isEmpty()) {
			return PropertyValueParseResultImp.empty();
		}

		return PropertyValueParseResultImp.of(new FlexBasisValue(lengthResult.getResult().get(), false), lengthResult.getLength());
	}
	
}
