package com.github.webicitybrowser.spec.css.parser.property.display;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.display.DisplayValue;
import com.github.webicitybrowser.spec.css.property.display.OuterDisplayType;

public class InnerOuterDisplayValueParser implements PropertyValueParser<DisplayValue> {

	@Override
	public PropertyValueParseResult<DisplayValue> parse(TokenLike[] tokens, int offset, int length) {
		// TODO Support InnerDisplayType
		if (!checkSelectorFormat(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}
		OuterDisplayType outerDisplayType = parseOuterDisplayType((IdentToken) tokens[0]);
		if (outerDisplayType == null) {
			return PropertyValueParseResultImp.empty();
		}
		
		return PropertyValueParseResultImp.of(new DisplayValue(outerDisplayType), length);
	}

	private OuterDisplayType parseOuterDisplayType(IdentToken identifier) {
		switch (identifier.getValue()) {
		case "inline":
			return OuterDisplayType.INLINE;
		case "block":
			return OuterDisplayType.BLOCK;
		default:
			return null;
		}
	}

	private boolean checkSelectorFormat(TokenLike[] tokens, int offset, int length) {
		if (length != 1) {
			return false;
		}
		return tokens[0] instanceof IdentToken;
	}

}
