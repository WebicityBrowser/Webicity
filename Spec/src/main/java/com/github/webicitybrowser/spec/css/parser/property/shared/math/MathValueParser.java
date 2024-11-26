package com.github.webicitybrowser.spec.css.parser.property.shared.math;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.componentvalue.FunctionValue;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.CommaToken;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.shared.math.MaxMathValue;
import com.github.webicitybrowser.spec.css.property.shared.math.MinMathValue;

public class MathValueParser implements PropertyValueParser<CSSValue> {

	private final PropertyValueParser<CSSValue> innerParser;

	public MathValueParser(PropertyValueParser<CSSValue> innerParser) {
		this.innerParser = innerParser;
	}
	
	@Override
	public PropertyValueParseResult<CSSValue> parse(TokenLike[] tokens, int offset, int length) {
		if (length >= 1 && tokens[offset] instanceof FunctionValue functionValue) {
			TokenLike[] functionTokens = stripWhitespace(functionValue.getValue());
			PropertyValueParseResult<CSSValue> result = switch (functionValue.getName()) {
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

	private PropertyValueParseResult<CSSValue> parseMinMax(TokenLike[] tokens, int offset, int length, boolean isMin) {
		List<CSSValue> values = new ArrayList<>();

		int priorTokens = 0;
		do {
			if (priorTokens != 0) {
				priorTokens++;
			}
			// TODO: Allow calc value
			PropertyValueParseResult<CSSValue> result = innerParser.parse(tokens, offset + priorTokens, length);
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
