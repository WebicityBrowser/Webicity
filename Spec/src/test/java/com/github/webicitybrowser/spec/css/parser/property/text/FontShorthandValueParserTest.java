package com.github.webicitybrowser.spec.css.parser.property.text;

import static com.github.webicitybrowser.spec.css.parser.property.PropertyParseTestUtil.createDimensionToken;
import static com.github.webicitybrowser.spec.css.parser.property.PropertyParseTestUtil.createNumberToken;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.StringToken;
import com.github.webicitybrowser.spec.css.property.font.FontValue;
import com.github.webicitybrowser.spec.css.property.fontfamily.FontFamilyValue.FontFamilyEntry;
import com.github.webicitybrowser.spec.css.property.fontfamily.NamedFontFamilyEntry;
import com.github.webicitybrowser.spec.css.property.shared.length.AbsoluteLengthValue;
import com.github.webicitybrowser.spec.css.property.shared.length.AbsoluteLengthValue.AbsoluteLengthUnit;

public class FontShorthandValueParserTest {
	
	private FontShorthandValueParser fontShorthandValueParser;

	@BeforeEach
	public void setup() throws Exception {
		this.fontShorthandValueParser = new FontShorthandValueParser();
	}

	@Test
	@DisplayName("Can parse font shorthand with font families")
	public void canParseFontShorthandWithFontFamilies() {
		TokenLike[] tokens = new TokenLike[] {
			(StringToken) () -> "Arial"
		};

		PropertyValueParseResult<FontValue> fontResult = fontShorthandValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(fontResult.getResult().isPresent());
		FontValue fontValue = fontResult.getResult().get();
		assertFontIsArial(fontValue);
		Assertions.assertTrue(fontValue.fontSize().isEmpty());
		Assertions.assertTrue(fontValue.lineHeight().isEmpty());
	}

	@Test
	@DisplayName("Can parse font shorthand with font families and font size")
	public void canParseFontShorthandWithFontFamiliesAndFontSize() {
		TokenLike[] tokens = new TokenLike[] {
			createDimensionToken(1, "px"),
			(StringToken) () -> "Arial"
		};

		PropertyValueParseResult<FontValue> fontResult = fontShorthandValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(fontResult.getResult().isPresent());
		FontValue fontValue = fontResult.getResult().get();

		assertFontIsArial(fontValue);
		assertFontSizeIs1px(fontValue);

		Assertions.assertTrue(fontValue.lineHeight().isEmpty());
	}

	@Test
	@DisplayName("Can parse font shorthand with font families and font size and line height")
	public void canParseFontShorthandWithFontFamiliesAndFontSizeAndLineHeight() {
		TokenLike[] tokens = new TokenLike[] {
			createDimensionToken(1, "px"),
			(DelimToken) () -> '/',
			createNumberToken(1),
			(StringToken) () -> "Arial"
		};

		PropertyValueParseResult<FontValue> fontResult = fontShorthandValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(fontResult.getResult().isPresent());
		FontValue fontValue = fontResult.getResult().get();

		assertFontIsArial(fontValue);
		assertFontSizeIs1px(fontValue);

		Assertions.assertTrue(fontValue.lineHeight().isPresent());
	}

	private void assertFontIsArial(FontValue fontValue) {
		FontFamilyEntry[] fontFamilies = fontValue.fontFamilies().getEntries();
		Assertions.assertEquals(1, fontFamilies.length);
		Assertions.assertInstanceOf(NamedFontFamilyEntry.class, fontFamilies[0]);
		NamedFontFamilyEntry namedFontFamilyEntry = (NamedFontFamilyEntry) fontFamilies[0];
		Assertions.assertEquals("Arial", namedFontFamilyEntry.getName());
	}

	private void assertFontSizeIs1px(FontValue fontValue) {
		Assertions.assertTrue(fontValue.fontSize().isPresent());
		Assertions.assertInstanceOf(AbsoluteLengthValue.class, fontValue.fontSize().get());
		AbsoluteLengthValue absoluteLengthValue = (AbsoluteLengthValue) fontValue.fontSize().get();
		Assertions.assertEquals(1, absoluteLengthValue.getValue());
		Assertions.assertEquals(AbsoluteLengthUnit.PX, absoluteLengthValue.getUnit());
	}


}
