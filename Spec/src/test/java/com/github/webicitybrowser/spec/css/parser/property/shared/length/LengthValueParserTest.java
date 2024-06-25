package com.github.webicitybrowser.spec.css.parser.property.shared.length;

import static com.github.webicitybrowser.spec.css.parser.property.PropertyParseTestUtil.createDimensionToken;
import static com.github.webicitybrowser.spec.css.parser.property.PropertyParseTestUtil.createNumberToken;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.property.shared.length.AbsoluteLengthValue;
import com.github.webicitybrowser.spec.css.property.shared.length.AbsoluteLengthValue.AbsoluteLengthUnit;
import com.github.webicitybrowser.spec.css.property.shared.length.LengthValue;
import com.github.webicitybrowser.spec.css.property.shared.length.RelativeLengthValue;
import com.github.webicitybrowser.spec.css.property.shared.length.RelativeLengthValue.RelativeLengthUnit;

public class LengthValueParserTest {
	
	private LengthValueParser lengthValueParser;

	@BeforeEach
	public void setup() {
		lengthValueParser = new LengthValueParser();
	}

	@Test
	@DisplayName("Can parse absolute length")
	public void canParseAbsoluteLength() {
		TokenLike[] tokens = new TokenLike[] { createDimensionToken(400, "px") };
		PropertyValueParseResult<LengthValue> result = lengthValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertInstanceOf(AbsoluteLengthValue.class, result.getResult().get());
		AbsoluteLengthValue absoluteLengthValue = (AbsoluteLengthValue) result.getResult().get();
		Assertions.assertEquals(400, absoluteLengthValue.getValue());
		Assertions.assertEquals(AbsoluteLengthUnit.PX, absoluteLengthValue.getUnit());
	}

	@Test
	@DisplayName("Can parse relative length")
	public void canParseRelativeLength() {
		TokenLike[] tokens = new TokenLike[] { createDimensionToken(400, "em") };
		PropertyValueParseResult<LengthValue> result = lengthValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertInstanceOf(RelativeLengthValue.class, result.getResult().get());
		RelativeLengthValue relativeLengthValue = (RelativeLengthValue) result.getResult().get();
		Assertions.assertEquals(400, relativeLengthValue.getValue());
		Assertions.assertEquals(RelativeLengthUnit.EM, relativeLengthValue.getUnit());
	}

	@Test
	@DisplayName("Can parse zero")
	public void canParseZero() {
		TokenLike[] tokens = new TokenLike[] { createNumberToken(0) };
		PropertyValueParseResult<LengthValue> result = lengthValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertInstanceOf(AbsoluteLengthValue.class, result.getResult().get());
		AbsoluteLengthValue absoluteLengthValue = (AbsoluteLengthValue) result.getResult().get();
		Assertions.assertEquals(0, absoluteLengthValue.getValue());
		Assertions.assertEquals(AbsoluteLengthUnit.PX, absoluteLengthValue.getUnit());
	}

}
