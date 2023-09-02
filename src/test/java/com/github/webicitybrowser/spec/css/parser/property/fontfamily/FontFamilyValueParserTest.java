package com.github.webicitybrowser.spec.css.parser.property.fontfamily;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.tokens.CommaToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.StringToken;
import com.github.webicitybrowser.spec.css.property.fontfamily.FontFamilyValue;
import com.github.webicitybrowser.spec.css.property.fontfamily.NamedFontFamilyEntry;

public class FontFamilyValueParserTest {
	
	private FontFamilyValueParser fontFamilyValueParser;

	@BeforeEach
	public void setup() {
		fontFamilyValueParser = new FontFamilyValueParser();
	}

	@Test
	@DisplayName("Can parse single named font family")
	public void canParseSingleNamedFontFamily() {
		TokenLike[] tokens = new TokenLike[] {
			(StringToken) () -> "Arial"
		};
		PropertyValueParseResult<FontFamilyValue> fontFamilyResult = fontFamilyValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(fontFamilyResult.getResult().isPresent());
		FontFamilyValue fontFamilyValue = fontFamilyResult.getResult().get();
		Assertions.assertEquals(1, fontFamilyValue.getEntries().length);
		Assertions.assertInstanceOf(NamedFontFamilyEntry.class, fontFamilyValue.getEntries()[0]);
		NamedFontFamilyEntry namedFontFamilyEntry = (NamedFontFamilyEntry) fontFamilyValue.getEntries()[0];
		Assertions.assertEquals("Arial", namedFontFamilyEntry.getName());
	}

	@Test
	@DisplayName("Can parse multiple named font families")
	public void canParseMultipleNamedFontFamilies() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "Arial",
			new CommaToken() {},
			(StringToken) () -> "Helvetica"
		};
		PropertyValueParseResult<FontFamilyValue> fontFamilyResult = fontFamilyValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(fontFamilyResult.getResult().isPresent());
		FontFamilyValue fontFamilyValue = fontFamilyResult.getResult().get();
		Assertions.assertEquals(2, fontFamilyValue.getEntries().length);
		Assertions.assertInstanceOf(NamedFontFamilyEntry.class, fontFamilyValue.getEntries()[0]);
		NamedFontFamilyEntry namedFontFamilyEntry = (NamedFontFamilyEntry) fontFamilyValue.getEntries()[0];
		Assertions.assertEquals("Arial", namedFontFamilyEntry.getName());
		Assertions.assertInstanceOf(NamedFontFamilyEntry.class, fontFamilyValue.getEntries()[1]);
		namedFontFamilyEntry = (NamedFontFamilyEntry) fontFamilyValue.getEntries()[1];
		Assertions.assertEquals("Helvetica", namedFontFamilyEntry.getName());
	}

}
