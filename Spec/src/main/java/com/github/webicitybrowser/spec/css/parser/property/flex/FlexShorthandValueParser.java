package com.github.webicitybrowser.spec.css.parser.property.flex;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.imp.TokenStreamImp;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.EOFToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberToken;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexBasisValue;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexFactorValue;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexValue;

public class FlexShorthandValueParser implements PropertyValueParser<FlexValue> {
	
	@Override
	public PropertyValueParseResult<FlexValue> parse(TokenLike[] tokens, int offset, int length) {
		TokenStream stream = new TokenStreamImp(tokens, offset);

		FlexBasisValue basisValue = stream.peek() instanceof NumberToken ?
			null :
			parseBasisValue(stream);

		FlexFactorValue growFactor = null;
		FlexFactorValue shrinkFactor = null;
		if (stream.peek() instanceof NumberToken) {
			growFactor = new FlexFactorValue(((NumberToken) stream.read()).getValue().floatValue());
			if (stream.peek() instanceof NumberToken) {
				shrinkFactor = new FlexFactorValue(((NumberToken) stream.read()).getValue().floatValue());
			}
		}

		if (basisValue == null && !(stream.peek() instanceof EOFToken)) {
			basisValue = parseBasisValue(stream);
		}

		if (basisValue == null && growFactor == null && shrinkFactor == null) {
			return PropertyValueParseResultImp.empty();
		}

		int usedLength = stream.position() - offset;
		return PropertyValueParseResultImp.of(new FlexValue(growFactor, shrinkFactor, basisValue), usedLength);
	}

	private FlexBasisValue parseBasisValue(TokenStream stream) {
		// TODO: Implement
		stream.read();
		return null;
	}
	
}
