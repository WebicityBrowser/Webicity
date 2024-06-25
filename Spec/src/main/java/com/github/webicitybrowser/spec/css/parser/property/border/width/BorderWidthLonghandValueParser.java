package com.github.webicitybrowser.spec.css.parser.property.border.width;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.shared.length.AbsoluteLengthValue;

public class BorderWidthLonghandValueParser implements PropertyValueParser<CSSValue> {

	private final LineWidthValueParser lineWidthParser = new LineWidthValueParser();

	@Override
	public PropertyValueParseResult<CSSValue> parse(TokenLike[] tokens, int offset, int length) {
		if (length > 0 && tokens[offset] instanceof IdentToken identToken) {
			String name = identToken.getValue();
			return switch (name) {
				case "thin" -> PropertyValueParseResultImp.of(createAbsoluteLengthValue(1), 1);
				case "medium" -> PropertyValueParseResultImp.of(createAbsoluteLengthValue(3), 1);
				case "thick" -> PropertyValueParseResultImp.of(createAbsoluteLengthValue(5), 1);
				default -> lineWidthParser.parse(tokens, offset, length);
			};
		}

		return lineWidthParser.parse(tokens, offset, length);
	}

	private AbsoluteLengthValue createAbsoluteLengthValue(float value) {
		return new AbsoluteLengthValue() {
			@Override
			public AbsoluteLengthUnit getUnit() {
				return AbsoluteLengthUnit.PX;
			}

			@Override
			public float getValue() {
				return value;
			}
		};
	}
	
}
