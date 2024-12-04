package com.github.webicitybrowser.spec.css.parser.property.shared.math;

import java.util.ArrayList;
import java.util.List;

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
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.shared.math.MaxMathValue;
import com.github.webicitybrowser.spec.css.property.shared.math.MinMathValue;
import com.github.webicitybrowser.spec.css.property.shared.math.NumberMathValue;
import com.github.webicitybrowser.spec.css.property.shared.math.OperandMathValue;
import com.github.webicitybrowser.spec.css.property.shared.math.OperandMathValue.Operand;

public class MathValueParser implements PropertyValueParser<CSSValue> {

	private final PropertyValueParser<CSSValue> innerParser;

	public MathValueParser(PropertyValueParser<CSSValue> innerParser) {
		this.innerParser = innerParser;
	}
	
	@Override
	public PropertyValueParseResult<CSSValue> parse(TokenLike[] tokens, int offset, int length) {
		if (length >= 1 && tokens[offset] instanceof FunctionValue functionValue) {
			TokenLike[] functionTokens = stripWhitespace(functionValue.value());
			PropertyValueParseResult<CSSValue> result = switch (functionValue.name()) {
				case "calc" -> parseCalc(functionTokens, 0, functionTokens.length, false);
				case "min" -> parseMinMax(functionTokens, 0, functionTokens.length, true);
				case "max" -> parseMinMax(functionTokens, 0, functionTokens.length, false);
				default -> PropertyValueParseResultImp.empty();
			};

			if (
				result.getResult().isPresent()
				&& result.getLength() == functionTokens.length
			) {
				return PropertyValueParseResultImp.of(result.getResult().get(), 1);
			}
		}
		
		return innerParser.parse(tokens, offset, length);
	}

	private PropertyValueParseResult<CSSValue> parseCalc(TokenLike[] tokens, int offset, int length, boolean isMultiplicationPart) {
		if (length == 0) {
			return PropertyValueParseResultImp.empty();
		}

		PropertyValueParseResult<CSSValue> result = isMultiplicationPart ?
			parseFactor(tokens, offset, length) :
			parseCalc(tokens, offset, length, true);
		if (result.getResult().isEmpty()) {
			return PropertyValueParseResultImp.empty();
		}

		int currentOffset = offset + result.getLength();
		CSSValue currentMathValue = result.getResult().get();
		while (
			currentOffset < length
			&& tokens[currentOffset] instanceof DelimToken delimToken
			&& (
				(!isMultiplicationPart && delimToken.value() == '+' || delimToken.value() == '-') ||
				(isMultiplicationPart && delimToken.value() == '*' || delimToken.value() == '/'))
		) {
			PropertyValueParseResult<CSSValue> nextResult = isMultiplicationPart ?
				parseFactor(tokens, currentOffset + 1, length - currentOffset - 1) :
				parseCalc(tokens, currentOffset + 1, length - currentOffset - 1, true);
			if (nextResult.getResult().isEmpty()) {
				return PropertyValueParseResultImp.empty();
			}

			Operand operand = switch (delimToken.value()) {
				case '+' -> Operand.PLUS;
				case '-' -> Operand.MINUS;
				case '*' -> Operand.MULTIPLY;
				case '/' -> Operand.DIVIDE;
				default -> null;
			};
			if (operand == null) {
				return PropertyValueParseResultImp.empty();
			}

			currentMathValue = new OperandMathValue(
				operand,
				currentMathValue,
				nextResult.getResult().get()
			);
			currentOffset += nextResult.getLength() + 1;
		}

		return PropertyValueParseResultImp.of(currentMathValue, currentOffset - offset);
	}

	private PropertyValueParseResult<CSSValue> parseFactor(TokenLike[] tokens, int offset, int length) {
		if (length == 0) {
			return PropertyValueParseResultImp.empty();
		}

		if (tokens[offset] instanceof LParenToken) {
			PropertyValueParseResult<CSSValue> result = parseCalc(tokens, offset + 1, length - 1, false);
			if (result.getResult().isEmpty()) {
				return PropertyValueParseResultImp.empty();
			}

			if (offset + result.getLength() + 1 >= length || !(tokens[offset + result.getLength() + 1] instanceof RParenToken)) {
				return PropertyValueParseResultImp.empty();
			}

			return PropertyValueParseResultImp.of(result.getResult().get(), result.getLength() + 2);
		} else if (tokens[offset] instanceof NumberToken numberToken) {
			return PropertyValueParseResultImp.of(new NumberMathValue(numberToken.value()), 1);
		} else {
			return innerParser.parse(tokens, offset, length);
		}
	}

	private PropertyValueParseResult<CSSValue> parseMinMax(TokenLike[] tokens, int offset, int length, boolean isMin) {
		List<CSSValue> values = new ArrayList<>();

		int priorTokens = 0;
		do {
			if (priorTokens != 0) {
				priorTokens++;
			}
			// TODO: Allow calc value
			PropertyValueParseResult<CSSValue> result = parseCalc(tokens, offset + priorTokens, length, false);
			if (result.getResult().isEmpty()) {
				return PropertyValueParseResultImp.empty();
			}
			values.add(result.getResult().get());

			priorTokens += result.getLength();
		} while (offset + priorTokens < length && tokens[offset + priorTokens] instanceof CommaToken);

		CSSValue result = isMin ? new MinMathValue(values) : new MaxMathValue(values);
		return PropertyValueParseResultImp.of(result, priorTokens);
	}

	private TokenLike[] stripWhitespace(TokenLike[] tokens) {
		List<TokenLike> strippedTokens = new ArrayList<>();
		for (TokenLike token : tokens) {
			if (token instanceof WhitespaceToken) continue;
			strippedTokens.add(token);
		}
		return strippedTokens.toArray(TokenLike[]::new);
	}
	
}
