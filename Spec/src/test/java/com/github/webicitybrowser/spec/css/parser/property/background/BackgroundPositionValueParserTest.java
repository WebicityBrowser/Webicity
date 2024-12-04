package com.github.webicitybrowser.spec.css.parser.property.background;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.background.BackgroundPositionValueParser.BgPositionValueParser;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.PercentageToken;
import com.github.webicitybrowser.spec.css.property.background.BackgroundPositionValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundPositionValue.BackgroundAxisReference;
import com.github.webicitybrowser.spec.css.property.shared.percentage.PercentageValue;

public class BackgroundPositionValueParserTest {
	
	private BgPositionValueParser bgPositionValueParser;

	@BeforeEach
	public void setup() {
		bgPositionValueParser = new BgPositionValueParser();
	}

	@Test
	@DisplayName("Can parse position with one keyword")
	public void canParsePositionWithOneKeyword() {
		TokenLike[] tokens = new TokenLike[] {
			new IdentToken("top")
		};

		PropertyValueParseResult<BackgroundPositionValue> parseResult = bgPositionValueParser.parse(tokens, 0, tokens.length);
		BackgroundPositionValue result = parseResult.getResult().get();
		Assertions.assertEquals(BackgroundAxisReference.CENTER, result.horizontalPosition().reference());
		Assertions.assertEquals(BackgroundAxisReference.TOP, result.verticalPosition().reference());
	}

	@Test
	@DisplayName("Can parse position with one percentage")
	public void canParsePositionWithOnePercentage() {
		TokenLike[] tokens = new TokenLike[] {
			new PercentageToken(50)
		};

		PropertyValueParseResult<BackgroundPositionValue> parseResult = bgPositionValueParser.parse(tokens, 0, tokens.length);
		BackgroundPositionValue result = parseResult.getResult().get();
		Assertions.assertEquals(50, ((PercentageValue) result.horizontalPosition().offset()).getValue());
		Assertions.assertEquals(BackgroundAxisReference.CENTER, result.verticalPosition().reference());
	}

	@Test
	@DisplayName("Can parse position with two keywords")
	public void canParsePositionWithTwoKeywords() {
		TokenLike[] tokens = new TokenLike[] {
			new IdentToken("top"),
			new IdentToken("left")
		};

		PropertyValueParseResult<BackgroundPositionValue> parseResult = bgPositionValueParser.parse(tokens, 0, tokens.length);
		BackgroundPositionValue result = parseResult.getResult().get();
		Assertions.assertEquals(BackgroundAxisReference.LEFT, result.horizontalPosition().reference());
		Assertions.assertEquals(BackgroundAxisReference.TOP, result.verticalPosition().reference());
	}

	@Test
	@DisplayName("Can parse position with two percentages")
	public void canParsePositionWithTwoPercentages() {
		TokenLike[] tokens = new TokenLike[] {
			new PercentageToken(40),
			new PercentageToken(60)
		};

		PropertyValueParseResult<BackgroundPositionValue> parseResult = bgPositionValueParser.parse(tokens, 0, tokens.length);
		BackgroundPositionValue result = parseResult.getResult().get();
		Assertions.assertEquals(40, ((PercentageValue) result.horizontalPosition().offset()).getValue());
		Assertions.assertEquals(60, ((PercentageValue) result.verticalPosition().offset()).getValue());
	}

	@Test
	@DisplayName("Can parse position with one keyword and one percentage")
	public void canParsePositionWithOneKeywordAndOnePercentage() {
		TokenLike[] tokens = new TokenLike[] {
			new PercentageToken(40),
			new IdentToken("top"),
		};

		PropertyValueParseResult<BackgroundPositionValue> parseResult = bgPositionValueParser.parse(tokens, 0, tokens.length);
		BackgroundPositionValue result = parseResult.getResult().get();
		Assertions.assertEquals(40, ((PercentageValue) result.horizontalPosition().offset()).getValue());
		Assertions.assertEquals(BackgroundAxisReference.TOP, result.verticalPosition().reference());
	}

	@Test
	@DisplayName("Can parse position with two keyword-percentage pairs")
	public void canParsePositionWithTwoKeywordPercentagePairs() {
		TokenLike[] tokens = new TokenLike[] {
			new IdentToken("top"),
			new PercentageToken(40),
			new IdentToken("right"),
			new PercentageToken(60)
		};

		PropertyValueParseResult<BackgroundPositionValue> parseResult = bgPositionValueParser.parse(tokens, 0, tokens.length);
		BackgroundPositionValue result = parseResult.getResult().get();
		Assertions.assertEquals(BackgroundAxisReference.RIGHT, result.horizontalPosition().reference());
		Assertions.assertEquals(60, ((PercentageValue) result.horizontalPosition().offset()).getValue());
		Assertions.assertEquals(BackgroundAxisReference.TOP, result.verticalPosition().reference());
		Assertions.assertEquals(40, ((PercentageValue) result.verticalPosition().offset()).getValue());
	}

}
