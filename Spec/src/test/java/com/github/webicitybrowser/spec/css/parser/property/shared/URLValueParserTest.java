package com.github.webicitybrowser.spec.css.parser.property.shared;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.tokens.FunctionToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RParenToken;
import com.github.webicitybrowser.spec.css.parser.tokens.StringToken;
import com.github.webicitybrowser.spec.css.parser.tokens.URLToken;
import com.github.webicitybrowser.spec.css.property.shared.URLValue;

public class URLValueParserTest {
	
	private URLValueParser urlValueParser;

	@BeforeEach
	public void setup() {
		urlValueParser = new URLValueParser();
	}

	@Test
	@DisplayName("Can parse URL with URL-token")
	public void canParseURLWithUrlToken() {
		TokenLike[] tokens = new TokenLike[] { (URLToken) () -> "https://example.com" };
		PropertyValueParseResult<URLValue> result = urlValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals("https://example.com", result.getResult().get().url());
	}

	@Test
	@DisplayName("Can parse URL with string")
	public void canParseURLWithString() {
		TokenLike[] tokens = new TokenLike[] {
			(FunctionToken) () -> "url",
			(StringToken) () -> "https://example.com",
			new RParenToken() {}
		};
		PropertyValueParseResult<URLValue> result = urlValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals("https://example.com", result.getResult().get().url());
	}

}
