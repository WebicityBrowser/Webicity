package com.github.webicitybrowser.spec.css.parser.property.floatbox;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.floatbox.FloatValue;

public class FloatValueParserTest {
	
	private FloatValueParser floatValueParser;

	@BeforeEach
	public void setup() {
		floatValueParser = new FloatValueParser();
	}

	@Test
	@DisplayName("Can parse float value")
	public void canParseFloatValue() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "left"
		};

		PropertyValueParseResult<FloatValue> parseResult = floatValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(parseResult.getResult().isPresent());
		Assertions.assertEquals(1, parseResult.getLength());

		FloatValue floatValue = parseResult.getResult().get();
		Assertions.assertEquals(FloatValue.LEFT, floatValue);

	}

}
