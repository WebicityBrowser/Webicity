package com.github.webicitybrowser.spec.css.parser.property.color;

import com.github.webicitybrowser.spec.css.componentvalue.FunctionValue;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.imp.TokenStreamImp;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.CommaToken;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.EOFToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberToken;
import com.github.webicitybrowser.spec.css.parser.tokens.PercentageToken;
import com.github.webicitybrowser.spec.css.parser.util.TokenUtils;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public class RGBColorValueParser implements PropertyValueParser<ColorValue> {

	@Override
	public PropertyValueParseResult<ColorValue> parse(TokenLike[] tokens, int offset, int length) {
		if (length < 1 || !(tokens[offset] instanceof FunctionValue)) {
			return PropertyValueParseResultImp.empty();
		}

		FunctionValue functionValue = (FunctionValue) tokens[offset];
		if (!(functionValue.getName().equals("rgb") || functionValue.getName().equals("rgba"))) {
			return PropertyValueParseResultImp.empty();
		}

		TokenLike[] valueTokens = TokenUtils.stripWhitespace(functionValue.getValue());
		TokenStream stream = new TokenStreamImp(valueTokens, offset);
		
		float[] rgbComponents = parseRGBComponents(stream);
		for (float component : rgbComponents) {
			if (component == -1) {
				return PropertyValueParseResultImp.empty();
			}
		}

		float alphaComponent = 255;
		if (!(stream.peek() instanceof EOFToken)) {
			if ((stream.peek() instanceof DelimToken delimToken && delimToken.getValue() == '/') || stream.peek() instanceof CommaToken) {
				stream.read();
			}
			alphaComponent = parseAlphaComponent(stream.read());
		}

		if (alphaComponent == -1 || !(stream.peek() instanceof EOFToken)) {
			return PropertyValueParseResultImp.empty();
		}

		return PropertyValueParseResultImp.of(
			new RGBColorValueImp(
				(int) rgbComponents[0],
				(int) rgbComponents[1],
				(int) rgbComponents[2],
				(int) alphaComponent),
			1
		);
	}

	private float[] parseRGBComponents(TokenStream stream) {
		// TODO: What if first token is none?
		boolean usePercentage = false;
		if (stream.peek() instanceof PercentageToken) {
			usePercentage = true;
		}

		float red = parseComponent(stream.read(), usePercentage);
		boolean useComma = stream.peek() instanceof CommaToken;
		if (useComma) {
			stream.read();
		}

		float green = parseComponent(stream.read(), usePercentage);
		if (useComma) {
			if (!(stream.peek() instanceof CommaToken)) {
				return new float[] { -1, -1, -1 };
			}
			stream.read();
		}

		float blue = parseComponent(stream.read(), usePercentage);

		return new float[] { red, green, blue };
	}
	
	private float parseComponent(TokenLike component, boolean usePercentage) {
		// TODO: Support none keyword
		if (usePercentage) {
			return parsePercentageComponent(component);
		} else {
			return parseNumberComponent(component, 255);
		}
	}

	private float parseAlphaComponent(TokenLike component) {
		if (component instanceof NumberToken) {
			return parseNumberComponent(component, 1) * 255;
		} else if (component instanceof PercentageToken) {
			return parsePercentageComponent(component);
		} else {
			return -1;
		}
	}

	private float parseNumberComponent(TokenLike component, float maxValue) {
		if (component instanceof NumberToken numberToken) {
			float value = numberToken.getValue().floatValue();
			if (value < 0 || value > maxValue) {
				return -1;
			}
			return value;
		} else {
			return -1;
		}
	}

	private float parsePercentageComponent(TokenLike component) {
		if (component instanceof PercentageToken percentageToken) {
			float value = percentageToken.getValue().floatValue();
			if (value < 0 || value > 100) {
				return -1;
			}

			return value * 255 / 100;
		} else {
			return -1;
		}
	}
	
}
