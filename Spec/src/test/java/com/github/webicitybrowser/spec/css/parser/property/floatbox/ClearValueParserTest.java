package com.github.webicitybrowser.spec.css.parser.property.floatbox;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.floatbox.ClearValue;

public class ClearValueParserTest {

	private ClearValueParser clearValueParser;

	@BeforeEach
	public void setup() {
		clearValueParser = new ClearValueParser();
	}

	@Test
	@DisplayName("Can parse clear value")
	public void canParseClearValue() {
		TokenLike[] tokens = new TokenLike[] {
			new IdentToken("left")
		};

		PropertyValueParseResult<ClearValue> parseResult = clearValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(parseResult.getResult().isPresent());
		Assertions.assertEquals(1, parseResult.getLength());

		ClearValue clearValue = parseResult.getResult().get();
		Assertions.assertEquals(ClearValue.LEFT, clearValue);

	}

}
