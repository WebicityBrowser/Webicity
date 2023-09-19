package com.github.webicitybrowser.spec.css.parser.property.text;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberToken;
import com.github.webicitybrowser.spec.css.property.fontweight.FontWeightValue;

public class FontWeightValueParser implements PropertyValueParser<FontWeightValue> {

	private static final int NORMAL = 400;
	private static final int BOLD = 700;

	@Override
	public PropertyValueParseResult<FontWeightValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkSelectorFormat(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}
		
		FontWeightValue fontWeightValue = parseFontWeightValue(tokens[0]);
		if (fontWeightValue == null) {
			return PropertyValueParseResultImp.empty();
		}
		
		return PropertyValueParseResultImp.of(fontWeightValue, 1);
	}

	private FontWeightValue parseFontWeightValue(TokenLike token) {
		if (token instanceof NumberToken) {
			int value = ((NumberToken) token).getValue().intValue();
			if (value < 1 || value > 1000) return null;
			return _1 -> value;
		} else if (token instanceof IdentToken IdentToken) {
			switch (IdentToken.getValue()) {
			case "normal":
				return _1 -> NORMAL;
			case "bold":
				return _1 -> BOLD;
			default:
				return null;
			}
		}
		return null;
	}

	private boolean checkSelectorFormat(TokenLike[] tokens, int offset, int length) {
		if (length < 1) return false;
		return tokens[0] instanceof TokenLike;
	}

}
