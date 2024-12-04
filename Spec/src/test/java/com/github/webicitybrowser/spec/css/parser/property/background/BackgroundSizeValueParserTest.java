package com.github.webicitybrowser.spec.css.parser.property.background;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.background.BackgroundSizeValueParser.BgSizeValueParser;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.PercentageToken;
import com.github.webicitybrowser.spec.css.property.background.BackgroundSizeValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundSizeValue.CoverBackgroundSizeValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundSizeValue.RelativeBackgroundSizeValue;
import com.github.webicitybrowser.spec.css.property.shared.basic.AutoValue;
import com.github.webicitybrowser.spec.css.property.shared.percentage.PercentageValue;

public class BackgroundSizeValueParserTest {
	
	private BgSizeValueParser bgSizeValueParser;

	@BeforeEach
	public void setup() {
		bgSizeValueParser = new BgSizeValueParser();
	}

	@Test
	@DisplayName("Can parse well-defined background size")
	public void canParseWellDefinedBackgroundSize() {
		TokenLike[] tokens = new TokenLike[] {
			new IdentToken("cover")
		};
		
		PropertyValueParseResult<BackgroundSizeValue> parseResult = bgSizeValueParser.parse(tokens, 0, tokens.length);
		BackgroundSizeValue result = parseResult.getResult().get();
		Assertions.assertInstanceOf(CoverBackgroundSizeValue.class, result);
	}

	@Test
	@DisplayName("Can parse background size with percentage and auto components")
	public void canParseBackgroundSizeWithPercentageAndAutoComponents() {
		TokenLike[] tokens = new TokenLike[] {
			new PercentageToken(50),
			new IdentToken("auto")
		};
		
		PropertyValueParseResult<BackgroundSizeValue> parseResult = bgSizeValueParser.parse(tokens, 0, tokens.length);
		BackgroundSizeValue result = parseResult.getResult().get();
		Assertions.assertInstanceOf(RelativeBackgroundSizeValue.class, result);
		RelativeBackgroundSizeValue relativeBackgroundSizeValue = (RelativeBackgroundSizeValue) result;
		Assertions.assertInstanceOf(PercentageValue.class, relativeBackgroundSizeValue.sizeX());
		PercentageValue sizeX = (PercentageValue) relativeBackgroundSizeValue.sizeX();
		Assertions.assertEquals(50, sizeX.getValue());
		Assertions.assertInstanceOf(AutoValue.class, relativeBackgroundSizeValue.sizeY());
	}

	@Test
	@DisplayName("Can parse background size with collapsed component")
	public void canParseBackgroundSizeWithCollapsedComponent() {
		TokenLike[] tokens = new TokenLike[] {
			new IdentToken("auto")
		};
		
		PropertyValueParseResult<BackgroundSizeValue> parseResult = bgSizeValueParser.parse(tokens, 0, tokens.length);
		BackgroundSizeValue result = parseResult.getResult().get();
		Assertions.assertInstanceOf(RelativeBackgroundSizeValue.class, result);
		RelativeBackgroundSizeValue relativeBackgroundSizeValue = (RelativeBackgroundSizeValue) result;
		Assertions.assertInstanceOf(AutoValue.class, relativeBackgroundSizeValue.sizeX());
		Assertions.assertInstanceOf(AutoValue.class, relativeBackgroundSizeValue.sizeY());
	}

}
