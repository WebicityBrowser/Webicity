package com.github.webicitybrowser.spec.css.parser.property.border;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.border.style.BorderStyleLonghandValueParser;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.border.BorderStyleValue;

public class BorderStyleValueParserTest {
	
	private BorderStyleLonghandValueParser borderStyleValueParser;

	@BeforeEach
	public void setup() {
		borderStyleValueParser = new BorderStyleLonghandValueParser();
	}

	@Test
	@DisplayName("Can parse named border style value")
	public void canParseNamedBorderStyleValue() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "dashed"
		};

		PropertyValueParseResult<BorderStyleValue> parseResult = borderStyleValueParser.parse(tokens, 0, 1);
		Assertions.assertTrue(parseResult.getResult().isPresent());
		Assertions.assertEquals(BorderStyleValue.DASHED, parseResult.getResult().get());
	}

	@Test
	@DisplayName("Can't parse invalid named border style value")
	public void canNotParseInvalidNamedBorderStyleValue() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "invalid"
		};

		PropertyValueParseResult<BorderStyleValue> parseResult = borderStyleValueParser.parse(tokens, 0, 1);
		Assertions.assertFalse(parseResult.getResult().isPresent());
	}

}
