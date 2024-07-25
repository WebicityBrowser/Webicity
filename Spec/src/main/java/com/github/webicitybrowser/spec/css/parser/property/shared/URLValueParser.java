package com.github.webicitybrowser.spec.css.parser.property.shared;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.FunctionToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RParenToken;
import com.github.webicitybrowser.spec.css.parser.tokens.StringToken;
import com.github.webicitybrowser.spec.css.parser.tokens.URLToken;
import com.github.webicitybrowser.spec.css.property.shared.URLValue;

public class URLValueParser implements PropertyValueParser<URLValue> {

	@Override
	public PropertyValueParseResult<URLValue> parse(TokenLike[] tokens, int offset, int length) {
		if (length < 1) return PropertyValueParseResultImp.empty();
		
		if (tokens[offset] instanceof URLToken urlToken) {
			return PropertyValueParseResultImp.of(new URLValue(urlToken.getValue()), 1);
		}

		if (length < 3) return PropertyValueParseResultImp.empty();
		if (isURLFunction(tokens[offset], tokens[offset + 1], tokens[offset + 2])) {
			StringToken stringToken = (StringToken) tokens[offset + 1];
			return PropertyValueParseResultImp.of(new URLValue(stringToken.getValue()), 3);
		}

		return PropertyValueParseResultImp.empty();
	}

	private boolean isURLFunction(TokenLike tokenLike, TokenLike tokenLike2, TokenLike tokenLike3) {
		boolean isURLorSrc = tokenLike instanceof FunctionToken functionToken
			&& (functionToken.getValue().equals("url")
			|| functionToken.getValue().equals("src"));
		boolean isString = tokenLike2 instanceof StringToken;
		boolean isRParen = tokenLike3 instanceof RParenToken;

		return isURLorSrc && isString && isRParen;
	}
	
}
