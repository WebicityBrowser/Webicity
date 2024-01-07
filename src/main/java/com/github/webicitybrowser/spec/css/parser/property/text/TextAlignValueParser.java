package com.github.webicitybrowser.spec.css.parser.property.text;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.text.TextAlignValue;

public class TextAlignValueParser implements PropertyValueParser<TextAlignValue> {
	
	@Override
	public PropertyValueParseResult<TextAlignValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkSelectorFormat(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}
		
		TextAlignValue textAlignValue = parseTextAlignValue(tokens[0]);
		if (textAlignValue == null) {
			return PropertyValueParseResultImp.empty();
		}
		
		return PropertyValueParseResultImp.of(textAlignValue, 1);
	}

	private TextAlignValue parseTextAlignValue(TokenLike token) {
		if (token instanceof IdentToken IdentToken) {
			return switch (IdentToken.getValue()) {
			case "start" -> TextAlignValue.START;
			case "end" -> TextAlignValue.END;
			case "left" -> TextAlignValue.LEFT;
			case "right" -> TextAlignValue.RIGHT;
			case "center" -> TextAlignValue.CENTER;
			case "justify" -> TextAlignValue.JUSTIFY;
			case "match-parent" -> TextAlignValue.MATCH_PARENT;
			case "justify-all" -> TextAlignValue.JUSTIFY_ALL;
			default -> null;
			};
		}
		return null;
	}

	private boolean checkSelectorFormat(TokenLike[] tokens, int offset, int length) {
		if (length < 1) return false;
		return tokens[0] instanceof TokenLike;
	}

}
