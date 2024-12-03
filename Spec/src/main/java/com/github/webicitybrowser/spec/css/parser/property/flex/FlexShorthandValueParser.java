package com.github.webicitybrowser.spec.css.parser.property.flex;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberToken;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexFactorValue;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexValue;

public class FlexShorthandValueParser implements PropertyValueParser<FlexValue> {

	private FlexBasisValueParser basisValueParser = new FlexBasisValueParser();
	
	@Override
	public PropertyValueParseResult<FlexValue> parse(TokenLike[] tokens, int offset, int length) {
		if (tokens.length == 0) {
			return PropertyValueParseResultImp.empty();
		}

		int usedLength = 0;
		CSSValue basisValue = null;

		if (!(tokens[offset] instanceof NumberToken)) {
			PropertyValueParseResult<CSSValue> result = basisValueParser.parse(tokens, offset, length);
			if (result.getResult().isPresent()) {
				basisValue = result.getResult().get();
				usedLength += result.getLength();
			}
		}

		FlexFactorValue growFactor = null;
		FlexFactorValue shrinkFactor = null;
		if (usedLength < length && tokens[offset + usedLength] instanceof NumberToken numberToken) {
			growFactor = new FlexFactorValue(numberToken.getValue().floatValue());
			usedLength++;
		}
		if (usedLength < length && tokens[offset + usedLength] instanceof NumberToken numberToken) {
			shrinkFactor = new FlexFactorValue(numberToken.getValue().floatValue());
			usedLength++;
		}

		if (basisValue == null && usedLength < length) {
			PropertyValueParseResult<CSSValue> result = basisValueParser.parse(tokens, offset + usedLength, length - usedLength);
			if (result.getResult().isPresent()) {
				basisValue = result.getResult().get();
				usedLength += result.getLength();
			}
		}

		if (basisValue == null && growFactor == null && shrinkFactor == null) {
			return PropertyValueParseResultImp.empty();
		}

		return PropertyValueParseResultImp.of(new FlexValue(growFactor, shrinkFactor, basisValue), usedLength);
	}
	
}
