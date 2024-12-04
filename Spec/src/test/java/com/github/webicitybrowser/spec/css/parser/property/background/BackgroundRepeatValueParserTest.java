package com.github.webicitybrowser.spec.css.parser.property.background;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.background.BackgroundRepeatValueParser.BgRepeatValueParser;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.background.BackgroundRepeatValue;

public class BackgroundRepeatValueParserTest {
	
	private BgRepeatValueParser bgRepeatValueParser;

	@BeforeEach
	public void setup() {
		bgRepeatValueParser = new BgRepeatValueParser();
	}

	@Test
	@DisplayName("Can parse single-component repeat value")
	public void canParseSingleComponentRepeatValue() {
		TokenLike[] tokens = new TokenLike[] {
			new IdentToken("repeat-x")
		};

		PropertyValueParseResult<BackgroundRepeatValue> parseResult = bgRepeatValueParser.parse(tokens, 0, tokens.length);
		BackgroundRepeatValue result = parseResult.getResult().get();
		Assertions.assertEquals(BackgroundRepeatValue.RepeatStyle.REPEAT, result.repeatX());
		Assertions.assertEquals(BackgroundRepeatValue.RepeatStyle.NO_REPEAT, result.repeatY());
	}

	@Test
	@DisplayName("Can parse double-component repeat value")
	public void canParseDoubleComponentRepeatValue() {
		TokenLike[] tokens = new TokenLike[] {
			new IdentToken("no-repeat"),
			new IdentToken("space")
		};

		PropertyValueParseResult<BackgroundRepeatValue> parseResult = bgRepeatValueParser.parse(tokens, 0, tokens.length);
		BackgroundRepeatValue result = parseResult.getResult().get();
		Assertions.assertEquals(BackgroundRepeatValue.RepeatStyle.NO_REPEAT, result.repeatX());
		Assertions.assertEquals(BackgroundRepeatValue.RepeatStyle.SPACE, result.repeatY());
	}

	@Test
	@DisplayName("Can parse collapsed double-component repeat value")
	public void canParseCollapsedDoubleComponentRepeatValue() {
		TokenLike[] tokens = new TokenLike[] {
			new IdentToken("round")
		};

		PropertyValueParseResult<BackgroundRepeatValue> parseResult = bgRepeatValueParser.parse(tokens, 0, tokens.length);
		BackgroundRepeatValue result = parseResult.getResult().get();
		Assertions.assertEquals(BackgroundRepeatValue.RepeatStyle.ROUND, result.repeatX());
		Assertions.assertEquals(BackgroundRepeatValue.RepeatStyle.ROUND, result.repeatY());
	}

}
