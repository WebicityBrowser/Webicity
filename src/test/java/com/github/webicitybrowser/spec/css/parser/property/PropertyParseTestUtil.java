package com.github.webicitybrowser.spec.css.parser.property;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.tokens.DimensionToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberTypeFlag;

public final class PropertyParseTestUtil {
	
	private PropertyParseTestUtil() {}

	public static TokenLike createDimensionToken(int i, String string) {
		return new DimensionToken() {

			@Override
			public Number getValue() {
				return i;
			}

			@Override
			public NumberTypeFlag getTypeFlag() {
				return NumberTypeFlag.INTEGER;
			}

			@Override
			public String getUnit() {
				return string;
			}
			
		};
	}

	public static TokenLike createNumberToken(int i) {
		return new NumberToken() {

			@Override
			public Number getValue() {
				return i;
			}

			@Override
			public NumberTypeFlag getTypeFlag() {
				return NumberTypeFlag.INTEGER;
			}

		};
	}

}
