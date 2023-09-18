package com.github.webicitybrowser.spec.css.parser.property.boxsizing;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.boxsizing.BoxSizingValue;

public class BoxSizingValueParser implements PropertyValueParser<BoxSizingValue> {

	@Override
	public PropertyValueParseResult<BoxSizingValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkSelectorValid(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}

		IdentToken identToken = (IdentToken) tokens[offset];
		String ident = identToken.getValue();
		if (ident.equals("content-box")) {
			return PropertyValueParseResultImp.of(BoxSizingValue.CONTENT_BOX, 1);
		} else if (ident.equals("border-box")) {
			return PropertyValueParseResultImp.of(BoxSizingValue.BORDER_BOX, 1);
		} else {
			return PropertyValueParseResultImp.empty();
		}
	}

	private boolean checkSelectorValid(TokenLike[] tokens, int offset, int length) {
		return length > 0 && tokens[offset] instanceof IdentToken;
	}
	
}
