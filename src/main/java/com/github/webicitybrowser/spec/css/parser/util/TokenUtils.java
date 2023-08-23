package com.github.webicitybrowser.spec.css.parser.util;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;

public final class TokenUtils {

	private TokenUtils() {}

	public static TokenLike[] stripWhitespace(TokenLike[] value) {
		TokenLike[] sanitized = new TokenLike[value.length];

		int j = 0;
		for (int i = 0; i < value.length; i++) {
			TokenLike token = value[i];
			if (token instanceof WhitespaceToken) {
				continue;
			}
			sanitized[j++] = token;
		}

		TokenLike[] result = new TokenLike[j];
		System.arraycopy(sanitized, 0, result, 0, j);
		
		return result;
	}

}
