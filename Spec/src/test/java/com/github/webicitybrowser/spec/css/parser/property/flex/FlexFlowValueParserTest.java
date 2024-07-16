package com.github.webicitybrowser.spec.css.parser.property.flex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexDirectionValue;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexFlowValue;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexWrapValue;

public class FlexFlowValueParserTest {

	private FlexFlowValueParser parser;

	@BeforeEach
	public void setup() throws Exception {
		parser = new FlexFlowValueParser();
	}

	@Test
	@DisplayName("Can parse flex flow value")
	public void canParseFlexFlowValue() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "row",
			(IdentToken) () -> "wrap"
		};

		PropertyValueParseResult<FlexFlowValue> parseResult = parser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(parseResult.getResult().isPresent());
		Assertions.assertEquals(2, parseResult.getLength());
		FlexFlowValue value = parseResult.getResult().get();
		Assertions.assertEquals(FlexDirectionValue.ROW, value.flexDirection());
		Assertions.assertEquals(FlexWrapValue.WRAP, value.flexWrap());
	}
	
}
