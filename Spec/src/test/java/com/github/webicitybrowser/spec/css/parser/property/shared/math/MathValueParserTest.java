package com.github.webicitybrowser.spec.css.parser.property.shared.math;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.componentvalue.FunctionValue;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.CommaToken;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.LParenToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RParenToken;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.shared.math.MinMathValue;
import com.github.webicitybrowser.spec.css.property.shared.math.NumberMathValue;
import com.github.webicitybrowser.spec.css.property.shared.math.OperandMathValue;
import com.github.webicitybrowser.spec.css.property.shared.math.OperandMathValue.Operand;

public class MathValueParserTest {

	private MathValueParser mathValueParser;

	@BeforeEach
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setup() {
		mathValueParser = new MathValueParser((PropertyValueParser<CSSValue>) (PropertyValueParser) new IntegerValueParser());
	}

	@Test
	@DisplayName("Can parse value with no math function")
	public void canParseValueWithNoMathFunction() {
		TokenLike[] tokens = new TokenLike[] { new IntegerToken(5) };
		PropertyValueParseResult<CSSValue> result = mathValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(new IntegerValue(5), result.getResult().get());
	}

	@Test
	@DisplayName("Can parse value with min function")
	public void canParseValueWithMinFunction() {
		TokenLike[] tokens = new TokenLike[] {
			createFunctionValue("min",
				new IntegerToken(5), new CommaToken(), new IntegerToken(3)
			)
		};
		PropertyValueParseResult<CSSValue> result = mathValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(
			new MinMathValue(List.of(new IntegerValue(5), new IntegerValue(3))),
			result.getResult().get());
	}

	@Test
	@DisplayName("Can parse calc with only number")
	public void canParseCalcWithNoOperators() {
		TokenLike[] tokens = new TokenLike[] {
			createFunctionValue("calc",
				new NumberToken(5)
			)
		};
		PropertyValueParseResult<CSSValue> result = mathValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(new NumberMathValue(5), result.getResult().get());
	}

	@Test
	@DisplayName("Can parse calc with only custom value")
	public void canParseCalcWithOnlyCustomValue() {
		TokenLike[] tokens = new TokenLike[] {
			createFunctionValue("calc",
				new IntegerToken(5)
			)
		};
		PropertyValueParseResult<CSSValue> result = mathValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(new IntegerValue(5), result.getResult().get());
	}

	@Test
	@DisplayName("Can parse calc with addition")
	public void canParseCalcWithAddition() {
		TokenLike[] tokens = new TokenLike[] {
			createFunctionValue("calc",
				new NumberToken(1),
				new DelimToken('+'),
				new NumberToken(2)
			)
		};
		PropertyValueParseResult<CSSValue> result = mathValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(new OperandMathValue(
			Operand.PLUS,
			new NumberMathValue(1),
			new NumberMathValue(2)
		), result.getResult().get());
	}

	@Test
	@DisplayName("Can parse calc with multiplication")
	public void canParseCalcWithMultiplication() {
		TokenLike[] tokens = new TokenLike[] {
			createFunctionValue("calc",
				new NumberToken(1),
				new DelimToken('*'),
				new NumberToken(2)
			)
		};
		PropertyValueParseResult<CSSValue> result = mathValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(new OperandMathValue(
			Operand.MULTIPLY,
			new NumberMathValue(1),
			new NumberMathValue(2)
		), result.getResult().get());
	}

	@Test
	@DisplayName("Can parse calc with parentheses")
	public void canParseCalcWithParentheses() {
		TokenLike[] tokens = new TokenLike[] {
			createFunctionValue("calc",
				new LParenToken(),
				new NumberToken(1),
				new RParenToken()
			)
		};
		PropertyValueParseResult<CSSValue> result = mathValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(new NumberMathValue(1), result.getResult().get());
	}

	@Test
	@DisplayName("Can parse calc with complex expression")
	public void canParseCalcWithComplexExpression() {
		TokenLike[] tokens = new TokenLike[] {
			createFunctionValue("calc",
				new LParenToken(),
				new NumberToken(1),
				new DelimToken('-'),
				new NumberToken(2),
				new RParenToken(),
				new DelimToken('/'),
				new NumberToken(3)
			)
		};
		PropertyValueParseResult<CSSValue> result = mathValueParser.parse(tokens, 0, tokens.length);
		Assertions.assertTrue(result.getResult().isPresent());
		Assertions.assertEquals(new OperandMathValue(
			Operand.DIVIDE,
			new OperandMathValue(
				Operand.MINUS,
				new NumberMathValue(1),
				new NumberMathValue(2)
			),
			new NumberMathValue(3)
		), result.getResult().get());
	}

	private static class IntegerValueParser implements PropertyValueParser<IntegerValue> {
		@Override
		public PropertyValueParseResult<IntegerValue> parse(TokenLike[] tokens, int offset, int length) {
			if (length > 0 && tokens[offset] instanceof IntegerToken) {
				return PropertyValueParseResultImp.of(new IntegerValue(((IntegerToken) tokens[offset]).value()), 1);
			} else {
				return PropertyValueParseResultImp.empty();
			}
		}
	}

	private static FunctionValue createFunctionValue(String name, TokenLike... values) {
		return new FunctionValue(name, values);
	}

	private static record IntegerToken(int value) implements TokenLike {}
	private static record IntegerValue(int value) implements CSSValue {}
	
}
