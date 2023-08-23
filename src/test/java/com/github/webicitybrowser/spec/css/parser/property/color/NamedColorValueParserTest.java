package com.github.webicitybrowser.spec.css.parser.property.color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public class NamedColorValueParserTest {
	
	private NamedColorValueParser namedColorValueParser;

	@BeforeEach
	public void setup() {
		namedColorValueParser = new NamedColorValueParser();
	}

	@Test
	@DisplayName("Can parse named color")
	public void canParseNamedColor() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "rebeccapurple"
		};

		PropertyValueParseResult<ColorValue> result = namedColorValueParser.parse(tokens, 0, 1);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(1, result.getLength());

		ColorValue colorValue = result.getResult().get();
		Assertions.assertEquals(102, colorValue.getRed());
		Assertions.assertEquals(51, colorValue.getGreen());
		Assertions.assertEquals(153, colorValue.getBlue());
		Assertions.assertEquals(255, colorValue.getAlpha());
	}

	@Test
	@DisplayName("Can't parse invalid named color")
	public void canNotParseInvalidNamedColor() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "invalid"
		};

		PropertyValueParseResult<ColorValue> result = namedColorValueParser.parse(tokens, 0, 1);
		Assertions.assertFalse(result.getResult().isPresent());
	}

}
