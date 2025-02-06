package com.github.webicitybrowser.spec.css.parser.property.border.width;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.shared.length.AbsoluteLengthValue;
import com.github.webicitybrowser.spec.css.property.shared.length.AbsoluteLengthValue.AbsoluteLengthUnit;

public class BorderWidthLonghandValueParser implements PropertyValueParser<CSSValue> {

	private final LineWidthValueParser lineWidthParser = new LineWidthValueParser();

	@Override
	public PropertyValueParseResult<CSSValue> parse(TokenLike[] tokens, int offset, int length) {
		if (length > 0 && tokens[offset] instanceof IdentToken identToken) {
			String name = identToken.value();
			return switch (name) {
				case "thin" -> PropertyValueParseResultImp.of(new AbsoluteLengthValue(1, AbsoluteLengthUnit.PX), 1);
				case "medium" -> PropertyValueParseResultImp.of(new AbsoluteLengthValue(3, AbsoluteLengthUnit.PX), 1);
				case "thick" -> PropertyValueParseResultImp.of(new AbsoluteLengthValue(5, AbsoluteLengthUnit.PX), 1);
				default -> lineWidthParser.parse(tokens, offset, length);
			};
		}

		return lineWidthParser.parse(tokens, offset, length);
	}
	
}
