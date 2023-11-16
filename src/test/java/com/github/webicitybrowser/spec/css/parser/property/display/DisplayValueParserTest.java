package com.github.webicitybrowser.spec.css.parser.property.display;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.display.DisplayValue;
import com.github.webicitybrowser.spec.css.property.display.InnerDisplayType;
import com.github.webicitybrowser.spec.css.property.display.OuterDisplayType;
import com.github.webicitybrowser.spec.css.property.floatbox.FloatValue;
import com.github.webicitybrowser.threadyweb.graphical.value.InnerDisplay;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class DisplayValueParserTest {

	private DisplayPropertyValueParser displayParser;

	@BeforeEach
	public void setup() {
		displayParser = new DisplayPropertyValueParser();
	}

	@Test
	public void canParseTableCellValue() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "table-cell",
		};

		PropertyValueParseResult<DisplayValue> parseResult = displayParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(parseResult.getResult().isPresent());
		Assertions.assertEquals(1, parseResult.getLength());

		DisplayValue displayValue = parseResult.getResult().get();
		Assertions.assertEquals(InnerDisplayType.TABLE_CELL, displayValue.innerDisplayType());
		Assertions.assertEquals(OuterDisplayType.BLOCK, displayValue.outerDisplayType());

	}

	@Test
	public void canParseTableRowValue() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "table-row",
		};

		PropertyValueParseResult<DisplayValue> parseResult = displayParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(parseResult.getResult().isPresent());
		Assertions.assertEquals(1, parseResult.getLength());

		DisplayValue displayValue = parseResult.getResult().get();
		Assertions.assertEquals(InnerDisplayType.TABLE_ROW, displayValue.innerDisplayType());
		Assertions.assertEquals(OuterDisplayType.BLOCK, displayValue.outerDisplayType());

	}

}
