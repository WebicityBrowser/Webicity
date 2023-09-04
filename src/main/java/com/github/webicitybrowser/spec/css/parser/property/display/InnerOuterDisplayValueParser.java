package com.github.webicitybrowser.spec.css.parser.property.display;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.display.DisplayValue;
import com.github.webicitybrowser.spec.css.property.display.InnerDisplayType;
import com.github.webicitybrowser.spec.css.property.display.OuterDisplayType;

public class InnerOuterDisplayValueParser implements PropertyValueParser<DisplayValue> {

	@Override
	public PropertyValueParseResult<DisplayValue> parse(TokenLike[] tokens, int offset, int length) {
		// TODO Support InnerDisplayType
		if (!checkSelectorFormat(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}
		OuterDisplayType outerDisplayType = parseOuterDisplayTypeSingle((IdentToken) tokens[0]);
		InnerDisplayType innerDisplayType = parseInnerDisplayTypeSingle((IdentToken) tokens[0]);
		if (outerDisplayType == null || innerDisplayType == null) {
			return PropertyValueParseResultImp.empty();
		}
		
		return PropertyValueParseResultImp.of(new DisplayValue(outerDisplayType, innerDisplayType), length);
	}

	private OuterDisplayType parseOuterDisplayTypeSingle(IdentToken identifier) {
		switch (identifier.getValue()) {
		case "inline":
		case "inline-block":
			return OuterDisplayType.INLINE;
		case "block":
		case "flex":
			return OuterDisplayType.BLOCK;
		default:
			return null;
		}
	}

	private InnerDisplayType parseInnerDisplayTypeSingle(IdentToken identToken) {
		switch (identToken.getValue()) {
		case "block":
		case "inline":
			return InnerDisplayType.FLOW;
		case "inline-block":
			return InnerDisplayType.FLOW_ROOT;
		case "flex":
			return InnerDisplayType.FLEX;
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
