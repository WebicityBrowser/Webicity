package com.github.webicitybrowser.spec.css.parser.property.fontweight;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberTypeFlag;
import com.github.webicitybrowser.spec.css.property.fontweight.FontWeightValue;

public class FontWeightValueParserTest {
	
	private FontWeightValueParser fontWeightValueParser;

	@BeforeEach
	public void setup() {
		fontWeightValueParser = new FontWeightValueParser();
	}

	@Test
	@DisplayName("Can parse numerical font weight")
	public void canParseNumericalFontWeight() {
		TokenLike[] tokens = new TokenLike[] { createNumberToken(400) };
		PropertyValueParseResult<FontWeightValue> result = fontWeightValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(400, result.getResult().get().getWeight(400));
	}

	@Test
	@DisplayName("Can parse normal font weight")
	public void canParseNormalFontWeight() {
		TokenLike[] tokens = new TokenLike[] { (IdentToken) () -> "normal" };
		PropertyValueParseResult<FontWeightValue> result = fontWeightValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(400, result.getResult().get().getWeight(400));
	}

	@Test
	@DisplayName("Can parse bold font weight")
	public void canParseBoldFontWeight() {
		TokenLike[] tokens = new TokenLike[] { (IdentToken) () -> "bold" };
		PropertyValueParseResult<FontWeightValue> result = fontWeightValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(700, result.getResult().get().getWeight(400));
	}

	private TokenLike createNumberToken(int i) {
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
