package com.github.webicitybrowser.spec.css.parser.property.text;

import java.util.Optional;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.flow.LineHeightValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.font.FontValue;
import com.github.webicitybrowser.spec.css.property.fontfamily.FontFamilyValue;

public class FontShorthandValueParser implements PropertyValueParser<FontValue> {

	private final FontSizeValueParser fontSizeValueParser = new FontSizeValueParser();
	private final FontFamilyValueParser fontFamilyValueParser = new FontFamilyValueParser();
	private final LineHeightValueParser lineHeightValueParser = new LineHeightValueParser();

	@Override
	public PropertyValueParseResult<FontValue> parse(TokenLike[] tokens, int offset, int length) {
		return parseNonSystemFont(tokens, offset, length);
	}

	private PropertyValueParseResult<FontValue> parseNonSystemFont(TokenLike[] tokens, int offset, int length) {
		int usedLength = 0;

		PropertyValueParseResult<CSSValue> fontSizeValue = fontSizeValueParser.parse(tokens, offset, length);
		Optional<CSSValue> fontSize = fontSizeValue.getResult();
		Optional<CSSValue> lineHeight = Optional.empty();
		if (fontSize.isPresent()) {
			usedLength += fontSizeValue.getLength();
			
			if (
				(offset + usedLength) < tokens.length &&
				tokens[offset + usedLength] instanceof DelimToken delimToken &&
				delimToken.getValue() == '/'
			) {
				usedLength++;
				
				PropertyValueParseResult<CSSValue> lineHeightValue = lineHeightValueParser.parse(tokens, offset + usedLength, length - usedLength);
				lineHeight = lineHeightValue.getResult();
				if (lineHeight.isPresent()) {
					usedLength += lineHeightValue.getLength();
				}
			}
		}

		PropertyValueParseResult<FontFamilyValue> fontFamilyValue = fontFamilyValueParser.parse(tokens, offset + usedLength, length - usedLength);
		if (fontFamilyValue.getResult().isEmpty()) return PropertyValueParseResultImp.empty();
		usedLength += fontFamilyValue.getLength();

		FontValue fontValue = new FontValue(fontFamilyValue.getResult().get(), fontSize, lineHeight);

		return PropertyValueParseResultImp.of(fontValue, usedLength);
	}
	
}
