package com.github.webicitybrowser.spec.css.parser.property.border.style;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.border.BorderStyleValue;

public class BorderStyleLonghandValueParser implements PropertyValueParser<BorderStyleValue> {

	@Override
	public PropertyValueParseResult<BorderStyleValue> parse(TokenLike[] tokens, int offset, int length) {
		if (length < 1 || !(tokens[offset] instanceof IdentToken)) {
			return PropertyValueParseResultImp.empty();
		}

		String name = ((IdentToken) tokens[offset]).getValue();
		try {
			BorderStyleValue borderStyleValue = BorderStyleValue.valueOf(name.toUpperCase());
			return PropertyValueParseResultImp.of(borderStyleValue, 1);
		} catch (IllegalArgumentException e) {
			return PropertyValueParseResultImp.empty();
		}
	}
	
	

}
