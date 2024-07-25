package com.github.webicitybrowser.spec.css.parser.property.background;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.property.shared.ImageValueParser;
import com.github.webicitybrowser.spec.css.parser.property.shared.ListParser;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundImageLayersValue;
import com.github.webicitybrowser.spec.css.property.shared.NoneValue;

public class BackgroundImageValueParser implements PropertyValueParser<BackgroundImageLayersValue> {

	private static final ListParser<CSSValue> listParser = new ListParser<>(new BgImageValueParser());

	@Override
	public PropertyValueParseResult<BackgroundImageLayersValue> parse(TokenLike[] tokens, int offset, int length) {
		List<CSSValue> images = listParser.parseList(tokens, offset, length);
		if (images.isEmpty()) return PropertyValueParseResultImp.empty();

		return PropertyValueParseResultImp.of(new BackgroundImageLayersValue(images), length);
	}

	public static class BgImageValueParser implements PropertyValueParser<CSSValue> {
		
		private final ImageValueParser imageValueParser = new ImageValueParser();

		@Override
		public PropertyValueParseResult<CSSValue> parse(TokenLike[] tokens, int offset, int length) {
			if (length >= 1 && tokens[offset] instanceof IdentToken identToken && identToken.getValue().equals("none")) {
				return PropertyValueParseResultImp.of(new NoneValue(), 1);
			}

			return imageValueParser.parse(tokens, offset, length);
		}
		

	}
	
}
