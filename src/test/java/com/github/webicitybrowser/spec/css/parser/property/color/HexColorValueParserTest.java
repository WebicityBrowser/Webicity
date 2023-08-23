package com.github.webicitybrowser.spec.css.parser.property.color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.tokens.HashToken;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public class HexColorValueParserTest {
	
	private HexColorValueParser hexColorValueParser;

	@BeforeEach
	public void setup() {
		hexColorValueParser = new HexColorValueParser();
	}

	@Test
	@DisplayName("Can parse 6 digit hex color")
	public void canParse6DigitHexColor() {
		TokenLike[] tokens = new TokenLike[] {
			createHashToken("ff0000")
		};

		PropertyValueParseResult<ColorValue> result = hexColorValueParser.parse(tokens, 0, 1);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(1, result.getLength());

		ColorValue colorValue = result.getResult().get();
		Assertions.assertEquals(255, colorValue.getRed());
		Assertions.assertEquals(0, colorValue.getGreen());
		Assertions.assertEquals(0, colorValue.getBlue());
		Assertions.assertEquals(255, colorValue.getAlpha());
	}

	@Test
	@DisplayName("Can parse 3 digit hex color")
	public void canParse3DigitHexColor() {
		TokenLike[] tokens = new TokenLike[] {
			createHashToken("f00")
		};

		PropertyValueParseResult<ColorValue> result = hexColorValueParser.parse(tokens, 0, 1);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(1, result.getLength());

		ColorValue colorValue = result.getResult().get();
		Assertions.assertEquals(255, colorValue.getRed());
		Assertions.assertEquals(0, colorValue.getGreen());
		Assertions.assertEquals(0, colorValue.getBlue());
		Assertions.assertEquals(255, colorValue.getAlpha());
	}

	@Test
	@DisplayName("Can parse 8 digit hex color")
	public void canParse8DigitHexColor() {
		TokenLike[] tokens = new TokenLike[] {
			createHashToken("ff000011")
		};

		PropertyValueParseResult<ColorValue> result = hexColorValueParser.parse(tokens, 0, 1);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(1, result.getLength());

		ColorValue colorValue = result.getResult().get();
		Assertions.assertEquals(255, colorValue.getRed());
		Assertions.assertEquals(0, colorValue.getGreen());
		Assertions.assertEquals(0, colorValue.getBlue());
		Assertions.assertEquals(17, colorValue.getAlpha());
	}

	@Test
	@DisplayName("Can parse 4 digit hex color")
	public void canParse4DigitHexColor() {
		TokenLike[] tokens = new TokenLike[] {
			createHashToken("f001")
		};

		PropertyValueParseResult<ColorValue> result = hexColorValueParser.parse(tokens, 0, 1);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(1, result.getLength());

		ColorValue colorValue = result.getResult().get();
		Assertions.assertEquals(255, colorValue.getRed());
		Assertions.assertEquals(0, colorValue.getGreen());
		Assertions.assertEquals(0, colorValue.getBlue());
		Assertions.assertEquals(17, colorValue.getAlpha());
	}

	@Test
	@DisplayName("Cannot parse 5 digit hex color")
	public void cannotParse5DigitHexColor() {
		TokenLike[] tokens = new TokenLike[] {
			createHashToken("ff000")
		};

		PropertyValueParseResult<ColorValue> result = hexColorValueParser.parse(tokens, 0, 1);
		Assertions.assertFalse(result.getResult().isPresent());
	}

	private HashToken createHashToken(String value) {
		return new HashToken() {
			@Override
			public String getValue() {
				return value;
			}

			@Override
			public HashTypeFlag getTypeFlag() {
				return HashTypeFlag.UNRESTRICTED;
			}
		};
	}

}
