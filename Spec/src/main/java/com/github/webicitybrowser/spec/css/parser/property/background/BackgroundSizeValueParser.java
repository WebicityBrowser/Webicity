package com.github.webicitybrowser.spec.css.parser.property.background;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.property.shared.ListParser;
import com.github.webicitybrowser.spec.css.parser.property.shared.lengthpercentage.LengthPercentageValueParser;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundSizeValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundSizeValue.ContainBackgroundSizeValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundSizeValue.CoverBackgroundSizeValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundSizeValue.RelativeBackgroundSizeValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundSizeLayersValue;
import com.github.webicitybrowser.spec.css.property.shared.basic.AutoValue;

public class BackgroundSizeValueParser implements PropertyValueParser<BackgroundSizeLayersValue> {

	private final ListParser<BackgroundSizeValue> listParser = new ListParser<>(new BgSizeValueParser());

	@Override
	public PropertyValueParseResult<BackgroundSizeLayersValue> parse(TokenLike[] tokens, int offset, int length) {
		List<BackgroundSizeValue> sizes = listParser.parseList(tokens, offset, length);
		if (sizes.isEmpty()) return PropertyValueParseResultImp.empty();

		return PropertyValueParseResultImp.of(new BackgroundSizeLayersValue(sizes), sizes.size());
	}

	public static class BgSizeValueParser implements PropertyValueParser<BackgroundSizeValue> {

		private static final LengthPercentageValueParser lengthPercentageValueParser = new LengthPercentageValueParser();

		@Override
		public PropertyValueParseResult<BackgroundSizeValue> parse(TokenLike[] tokens, int offset, int length) {
			if (length < 1) return PropertyValueParseResultImp.empty();

			if (tokens[offset] instanceof IdentToken identToken) {
				if (identToken.value().equals("cover")) {
					return PropertyValueParseResultImp.of(new CoverBackgroundSizeValue(), 1);
				} else if (identToken.value().equals("contain")) {
					return PropertyValueParseResultImp.of(new ContainBackgroundSizeValue(), 1);
				}
			}

			PropertyValueParseResult<CSSValue> sizeXResult = parseComponent(tokens, offset, length);
			if (sizeXResult.getResult().isEmpty()) return PropertyValueParseResultImp.empty();
			CSSValue sizeX = sizeXResult.getResult().get();

			PropertyValueParseResult<CSSValue> sizeYResult = parseComponent(
				tokens, offset + sizeXResult.getLength(), length - sizeXResult.getLength());
			if (sizeYResult.getResult().isPresent()) {
				return PropertyValueParseResultImp.of(
					new RelativeBackgroundSizeValue(sizeX, sizeYResult.getResult().get()),
					sizeXResult.getLength() + sizeYResult.getLength());
			} else {
				return PropertyValueParseResultImp.of(
					new RelativeBackgroundSizeValue(sizeX, sizeX),
					sizeXResult.getLength());
			}
		}

		private PropertyValueParseResult<CSSValue> parseComponent(TokenLike[] tokens, int offset, int length) {
			if (length < 1) return PropertyValueParseResultImp.empty();
			if (tokens[offset] instanceof IdentToken identToken && identToken.value().equals("auto")) {
				return PropertyValueParseResultImp.of(new AutoValue(), 1);
			} else {
				return lengthPercentageValueParser.parse(tokens, offset, length).as(CSSValue.class);
			}
		}
		
	}
	
}
