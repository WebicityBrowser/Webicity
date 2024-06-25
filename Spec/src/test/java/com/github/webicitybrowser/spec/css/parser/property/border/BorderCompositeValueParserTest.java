package com.github.webicitybrowser.spec.css.parser.property.border;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.border.composite.BorderCompositeValueParser;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.border.BorderCompositeValue;
import com.github.webicitybrowser.spec.css.property.border.BorderStyleValue;
import com.github.webicitybrowser.spec.css.property.shared.length.AbsoluteLengthValue;
import com.github.webicitybrowser.spec.css.property.shared.length.AbsoluteLengthValue.AbsoluteLengthUnit;

public class BorderCompositeValueParserTest {
	
	private BorderCompositeValueParser parser;

	@BeforeEach
	public void setup() throws Exception {
		parser = new BorderCompositeValueParser();
	}

	@Test
	@DisplayName("Can parse composite border value")
	public void canParseCompositeBorderValue() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "solid",
			(IdentToken) () -> "thin",
			(IdentToken) () -> "rebeccapurple"
		};

		PropertyValueParseResult<BorderCompositeValue> parseResult = parser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(parseResult.getResult().isPresent());
		Assertions.assertEquals(3, parseResult.getLength());
		BorderCompositeValue value = parseResult.getResult().get();
		Assertions.assertInstanceOf(AbsoluteLengthValue.class, value.width());
		Assertions.assertEquals(1, ((AbsoluteLengthValue) value.width()).getValue());
		Assertions.assertEquals(AbsoluteLengthUnit.PX, ((AbsoluteLengthValue) value.width()).getUnit());
		Assertions.assertEquals(BorderStyleValue.SOLID, value.style());
		Assertions.assertEquals(102, value.color().getRed());
	}

}
