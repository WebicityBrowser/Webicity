package com.github.webicitybrowser.spec.css.parser.property.shared.percentage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.tokens.PercentageToken;
import com.github.webicitybrowser.spec.css.property.shared.percentage.PercentageValue;

public class PercentageValueParserTest {
	
	private PercentageValueParser percentageValueParser;

	@BeforeEach
	public void setup() {
		percentageValueParser = new PercentageValueParser();
	}

	@Test
	@DisplayName("Can parse percentage")
	public void canParsePercentage() {
		TokenLike[] tokens = new TokenLike[] { (PercentageToken) () -> 100 };
		PropertyValueParseResult<PercentageValue> result = percentageValueParser.parse(tokens, 0, 1);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(1, result.getLength());
		Assertions.assertEquals(100, result.getResult().get().getValue());
	}

}
