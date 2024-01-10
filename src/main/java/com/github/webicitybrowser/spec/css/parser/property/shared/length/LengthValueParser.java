package com.github.webicitybrowser.spec.css.parser.property.shared.length;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.DimensionToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberToken;
import com.github.webicitybrowser.spec.css.property.shared.length.AbsoluteLengthValue;
import com.github.webicitybrowser.spec.css.property.shared.length.LengthValue;
import com.github.webicitybrowser.spec.css.property.shared.length.RelativeLengthValue;

public class LengthValueParser implements PropertyValueParser<LengthValue> {

	@Override
	public PropertyValueParseResult<LengthValue> parse(TokenLike[] tokens, int offset, int length) {
		if (length > 0 && offset < tokens.length && tokens[offset] instanceof NumberToken numberToken && numberToken.getValue().floatValue() == 0) {
			return PropertyValueParseResultImp.of(AbsoluteLengthValue.of(0, "px"), 1);
		}

		if (!checkSelectorFormat(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}

		DimensionToken token = (DimensionToken) tokens[offset];
		return creteParseResultFromDimensionToken(token);
	}

	private boolean checkSelectorFormat(TokenLike[] tokens, int offset, int length) {
		return length > 0 && offset < tokens.length && tokens[offset] instanceof DimensionToken;
	}

	private PropertyValueParseResult<LengthValue> creteParseResultFromDimensionToken(DimensionToken token) {
		switch (token.getUnit()) {
		case "cm":
		case "mm":
		case "Q":
		case "in":
		case "pc":
		case "pt":
		case "px":
			return PropertyValueParseResultImp.of(AbsoluteLengthValue.of(token.getValue(), token.getUnit()), 1);

		case "em":
		case "ex":
		case "cap":
		case "ch":
		case "ic":
		case "rem":
		case "lh":
		case "rlh":
		case "vw":
		case "vh":
		case "vi":
		case "vb":
		case "vmin":
		case "vmax":
			return PropertyValueParseResultImp.of(createRelativeLengthValue(token.getValue(), token.getUnit()), 1);

		default:
			return PropertyValueParseResultImp.empty();
		}		
	}

	private RelativeLengthValue createRelativeLengthValue(Number value, String unit) {
		return new RelativeLengthValue() {

			@Override
			public RelativeLengthUnit getUnit() {
				return RelativeLengthUnit.valueOf(unit.toUpperCase());
			}

			@Override
			public float getValue() {
				return value.floatValue();
			}
		};
	}
	
}
