package com.github.webicitybrowser.spec.css.parser.property.color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.spec.css.componentvalue.FunctionValue;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.tokens.CommaToken;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberToken;
import com.github.webicitybrowser.spec.css.parser.tokens.PercentageToken;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public class RGBColorValueParserTest {
	
	private RGBColorValueParser rgbColorValueParser;

	@BeforeEach
	public void setup() throws Exception {
		rgbColorValueParser = new RGBColorValueParser();
	}

	@Test
	@DisplayName("Can parse rgb(0,0,0)")
	public void canParseRGB000() throws Exception {
		TokenLike[] valueTokens = new TokenLike[] {
			new NumberToken(0),
			new CommaToken(),
			new NumberToken(0),
			new CommaToken(),
			new NumberToken(0)
		};

		FunctionValue functionValue = Mockito.mock(FunctionValue.class);
		Mockito.when(functionValue.name()).thenReturn("rgb");
		Mockito.when(functionValue.value()).thenReturn(valueTokens);

		PropertyValueParseResult<ColorValue> result = rgbColorValueParser.parse(new TokenLike[] { functionValue }, 0, 1);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(1, result.getLength());

		ColorValue colorValue = result.getResult().get();
		Assertions.assertEquals(0, colorValue.getRed());
		Assertions.assertEquals(0, colorValue.getGreen());
		Assertions.assertEquals(0, colorValue.getBlue());
		Assertions.assertEquals(255, colorValue.getAlpha());
	}

	@Test
	@DisplayName("Can parse rgb(0, 0, 0)")
	public void canParseRGB0_0_0() throws Exception {
		TokenLike[] valueTokens = new TokenLike[] {
			new NumberToken(0),
			new CommaToken(),
			new WhitespaceToken(),
			new NumberToken(0),
			new CommaToken(),
			new WhitespaceToken(),
			new NumberToken(0)
		};

		FunctionValue functionValue = Mockito.mock(FunctionValue.class);
		Mockito.when(functionValue.name()).thenReturn("rgb");
		Mockito.when(functionValue.value()).thenReturn(valueTokens);

		PropertyValueParseResult<ColorValue> result = rgbColorValueParser.parse(new TokenLike[] { functionValue }, 0, 1);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(1, result.getLength());

		ColorValue colorValue = result.getResult().get();
		Assertions.assertEquals(0, colorValue.getRed());
		Assertions.assertEquals(0, colorValue.getGreen());
		Assertions.assertEquals(0, colorValue.getBlue());
		Assertions.assertEquals(255, colorValue.getAlpha());
	}

	@Test
	@DisplayName("Can parse rgb(1,0,0)")
	public void canParseRGB100() throws Exception {
		TokenLike[] valueTokens = new TokenLike[] {
			new NumberToken(1),
			new CommaToken(),
			new NumberToken(0),
			new CommaToken(),
			new NumberToken(0)
		};

		FunctionValue functionValue = Mockito.mock(FunctionValue.class);
		Mockito.when(functionValue.name()).thenReturn("rgb");
		Mockito.when(functionValue.value()).thenReturn(valueTokens);

		PropertyValueParseResult<ColorValue> result = rgbColorValueParser.parse(new TokenLike[] { functionValue }, 0, 1);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(1, result.getLength());

		ColorValue colorValue = result.getResult().get();
		Assertions.assertEquals(1, colorValue.getRed());
		Assertions.assertEquals(0, colorValue.getGreen());
		Assertions.assertEquals(0, colorValue.getBlue());
		Assertions.assertEquals(255, colorValue.getAlpha());
	}

	@Test
	@DisplayName("Can parse rgb(0,0,0 1%)")
	public void canParseRGB0001p() throws Exception {
		TokenLike[] valueTokens = new TokenLike[] {
			new NumberToken(0),
			new CommaToken(),
			new NumberToken(0),
			new CommaToken(),
			new NumberToken(0),
			new WhitespaceToken(),
			new PercentageToken(1)
		};

		FunctionValue functionValue = Mockito.mock(FunctionValue.class);
		Mockito.when(functionValue.name()).thenReturn("rgb");
		Mockito.when(functionValue.value()).thenReturn(valueTokens);

		PropertyValueParseResult<ColorValue> result = rgbColorValueParser.parse(new TokenLike[] { functionValue }, 0, 1);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(1, result.getLength());

		ColorValue colorValue = result.getResult().get();
		Assertions.assertEquals(0, colorValue.getRed());
		Assertions.assertEquals(0, colorValue.getGreen());
		Assertions.assertEquals(0, colorValue.getBlue());
		Assertions.assertEquals(2, colorValue.getAlpha());
	}

	@Test
	@DisplayName("Can parse rgb(0,0,0/1)")
	public void canParseRGB000slash1p() throws Exception {
		TokenLike[] valueTokens = new TokenLike[] {
			new NumberToken(0),
			new CommaToken(),
			new NumberToken(0),
			new CommaToken(),
			new NumberToken(0),
			new DelimToken('/'),
			new NumberToken(1)
		};

		FunctionValue functionValue = Mockito.mock(FunctionValue.class);
		Mockito.when(functionValue.name()).thenReturn("rgb");
		Mockito.when(functionValue.value()).thenReturn(valueTokens);

		PropertyValueParseResult<ColorValue> result = rgbColorValueParser.parse(new TokenLike[] { functionValue }, 0, 1);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(1, result.getLength());

		ColorValue colorValue = result.getResult().get();
		Assertions.assertEquals(0, colorValue.getRed());
		Assertions.assertEquals(0, colorValue.getGreen());
		Assertions.assertEquals(0, colorValue.getBlue());
		Assertions.assertEquals(255, colorValue.getAlpha());
	}

	@Test
	@DisplayName("Can parse rgb(1%1%1%)")
	public void canParseRGB1p1p1p() {
		TokenLike[] valueTokens = new TokenLike[] {
			new PercentageToken(1),
			new PercentageToken(1),
			new PercentageToken(1)
		};

		FunctionValue functionValue = Mockito.mock(FunctionValue.class);
		Mockito.when(functionValue.name()).thenReturn("rgb");
		Mockito.when(functionValue.value()).thenReturn(valueTokens);

		PropertyValueParseResult<ColorValue> result = rgbColorValueParser.parse(new TokenLike[] { functionValue }, 0, 1);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(1, result.getLength());

		ColorValue colorValue = result.getResult().get();
		Assertions.assertEquals(2, colorValue.getRed());
		Assertions.assertEquals(2, colorValue.getGreen());
		Assertions.assertEquals(2, colorValue.getBlue());
		Assertions.assertEquals(255, colorValue.getAlpha());
	}

	@Test
	@DisplayName("Can parse rgba(255 255 255, .5)")
	public void canParseRGBA000_5() throws Exception {
		TokenLike[] valueTokens = new TokenLike[] {
			new NumberToken(255),
			new WhitespaceToken(),
			new NumberToken(255),
			new WhitespaceToken(),
			new NumberToken(255),
			new CommaToken(),
			new NumberToken(.5f),
		};

		FunctionValue functionValue = Mockito.mock(FunctionValue.class);
		Mockito.when(functionValue.name()).thenReturn("rgba");
		Mockito.when(functionValue.value()).thenReturn(valueTokens);

		PropertyValueParseResult<ColorValue> result = rgbColorValueParser.parse(new TokenLike[] { functionValue }, 0, 1);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(1, result.getLength());

		ColorValue colorValue = result.getResult().get();
		Assertions.assertEquals(255, colorValue.getRed());
		Assertions.assertEquals(255, colorValue.getGreen());
		Assertions.assertEquals(255, colorValue.getBlue());
		Assertions.assertEquals(127, colorValue.getAlpha());
	}

	@Test
	@DisplayName("Cannot parse wrong function name")
	public void cannotParseWrongFunctionName() {
		TokenLike[] valueTokens = new TokenLike[] {
			new NumberToken(0),
			new CommaToken(),
			new NumberToken(0),
			new CommaToken(),
			new NumberToken(0)
		};

		FunctionValue functionValue = Mockito.mock(FunctionValue.class);
		Mockito.when(functionValue.name()).thenReturn("wrong");
		Mockito.when(functionValue.value()).thenReturn(valueTokens);

		PropertyValueParseResult<ColorValue> result = rgbColorValueParser.parse(new TokenLike[] { functionValue }, 0, 1);
		Assertions.assertFalse(result.getResult().isPresent());
	}

	@Test
	@DisplayName("Cannot parse wrong number of arguments")
	public void cannotParseWrongNumberOfArguments() {
		TokenLike[] valueTokens = new TokenLike[] {
			new NumberToken(0),
			new CommaToken(),
			new NumberToken(0),
			new CommaToken(),
		};

		FunctionValue functionValue = Mockito.mock(FunctionValue.class);
		Mockito.when(functionValue.name()).thenReturn("rgb");
		Mockito.when(functionValue.value()).thenReturn(valueTokens);

		PropertyValueParseResult<ColorValue> result = rgbColorValueParser.parse(new TokenLike[] { functionValue }, 0, 1);
		Assertions.assertFalse(result.getResult().isPresent());
	}

	@Test
	@DisplayName("Cannot mix percentage and number")
	public void cannotMixPercentageAndNumber() {
		TokenLike[] valueTokens = new TokenLike[] {
			new NumberToken(0),
			new CommaToken(),
			new PercentageToken(0),
			new CommaToken(),
			new NumberToken(0)
		};

		FunctionValue functionValue = Mockito.mock(FunctionValue.class);
		Mockito.when(functionValue.name()).thenReturn("rgb");
		Mockito.when(functionValue.value()).thenReturn(valueTokens);

		PropertyValueParseResult<ColorValue> result = rgbColorValueParser.parse(new TokenLike[] { functionValue }, 0, 1);
		Assertions.assertFalse(result.getResult().isPresent());
	}

	@Test
	@DisplayName("Use of commas must be consistent")
	public void useOfCommasMustBeConsistent() {
		TokenLike[] valueTokens = new TokenLike[] {
			new NumberToken(0),
			new CommaToken(),
			new NumberToken(0),
			new NumberToken(0)
		};

		FunctionValue functionValue = Mockito.mock(FunctionValue.class);
		Mockito.when(functionValue.name()).thenReturn("rgb");
		Mockito.when(functionValue.value()).thenReturn(valueTokens);

		PropertyValueParseResult<ColorValue> result1 = rgbColorValueParser.parse(new TokenLike[] { functionValue }, 0, 1);
		Assertions.assertFalse(result1.getResult().isPresent());

		valueTokens = new TokenLike[] {
			new NumberToken(0),
			new NumberToken(0),
			new CommaToken(),
			new NumberToken(0)
		};

		functionValue = Mockito.mock(FunctionValue.class);
		Mockito.when(functionValue.name()).thenReturn("rgb");
		Mockito.when(functionValue.value()).thenReturn(valueTokens);

		PropertyValueParseResult<ColorValue> result2 = rgbColorValueParser.parse(new TokenLike[] { functionValue }, 0, 1);
		Assertions.assertFalse(result2.getResult().isPresent());
	}

}
