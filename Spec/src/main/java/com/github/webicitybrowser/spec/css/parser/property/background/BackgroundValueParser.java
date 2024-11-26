package com.github.webicitybrowser.spec.css.parser.property.background;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.background.BackgroundAttachmentValueParser.BgAttachmentValueParser;
import com.github.webicitybrowser.spec.css.parser.property.background.BackgroundImageValueParser.BgImageValueParser;
import com.github.webicitybrowser.spec.css.parser.property.background.BackgroundPositionValueParser.BgPositionValueParser;
import com.github.webicitybrowser.spec.css.parser.property.background.BackgroundRepeatValueParser.BgRepeatValueParser;
import com.github.webicitybrowser.spec.css.parser.property.background.BackgroundSizeValueParser.BgSizeValueParser;
import com.github.webicitybrowser.spec.css.parser.property.color.ColorValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.property.shared.ListParser;
import com.github.webicitybrowser.spec.css.parser.property.shared.anyorder.AnyOrderParser;
import com.github.webicitybrowser.spec.css.parser.property.shared.anyorder.AnyOrderParser.AnyOrderParserResult;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundPositionValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundSizeValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundValue.BackgroundLayer;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public class BackgroundValueParser implements PropertyValueParser<BackgroundValue> {

	// TODO: Support <visual-box>
	private static final AnyOrderParser anyOrderParser = new AnyOrderParser(List.of(
		new BgImageValueParser(),
		new PositionSizeParser(),
		new BgRepeatValueParser(),
		new BgAttachmentValueParser(),
		new ColorValueParser()
	));

	private static final ListParser<AnyOrderParserResult> listParser = new ListParser<>(anyOrderParser);

	@Override
	public PropertyValueParseResult<BackgroundValue> parse(TokenLike[] tokens, int offset, int length) {
		List<AnyOrderParserResult> results = listParser.parseList(tokens, offset, length);
		if (results.isEmpty()) return PropertyValueParseResultImp.empty();

		List<BackgroundLayer> layers = new ArrayList<>();
		for (int i = 0; i < results.size(); i++) {
			AnyOrderParserResult value = results.get(i);
			BackgroundLayer backgroundLayer = createBackgroundLayer(value, i == results.size() - 1);
			if (backgroundLayer == null) return PropertyValueParseResultImp.empty();
			layers.add(backgroundLayer);
		}

		return PropertyValueParseResultImp.of(new BackgroundValue(layers), length);
	}

	private BackgroundLayer createBackgroundLayer(AnyOrderParserResult anyOrderResult, boolean isFinalLayer) {
		List<CSSValue> anyOrderValues = anyOrderResult.results();
		ColorValue backgroundColor = (ColorValue) anyOrderValues.get(4);
		if (backgroundColor != null && !isFinalLayer) {
			return null;
		}
		
		return new BackgroundLayer(backgroundColor);
	}

	private static record PositionSizeComposite(CSSValue position, CSSValue size) implements CSSValue {}

	private static class PositionSizeParser implements PropertyValueParser<PositionSizeComposite> {

		private static final BgPositionValueParser positionParser = new BgPositionValueParser();
		private static final BgSizeValueParser sizeParser = new BgSizeValueParser();

		@Override
		public PropertyValueParseResult<PositionSizeComposite> parse(TokenLike[] tokens, int offset, int length) {
			PropertyValueParseResult<BackgroundPositionValue> positionResult = positionParser.parse(tokens, offset, length);
			if (!positionResult.getResult().isPresent()) return PropertyValueParseResultImp.empty();
			BackgroundPositionValue position = positionResult.getResult().get();
			int totalLength = positionResult.getLength();

			if (!(totalLength < length && tokens[offset + totalLength] instanceof DelimToken delimToken && delimToken.getValue() == '/')) {
				return PropertyValueParseResultImp.of(new PositionSizeComposite(position, null), totalLength);
			}
			totalLength++;

			PropertyValueParseResult<BackgroundSizeValue> sizeResult = sizeParser.parse(tokens, offset + totalLength, length - totalLength);
			if (!sizeResult.getResult().isPresent()) return PropertyValueParseResultImp.empty();
			BackgroundSizeValue size = sizeResult.getResult().get();
			totalLength += sizeResult.getLength();

			return PropertyValueParseResultImp.of(new PositionSizeComposite(position, size), totalLength);
		}
	
		
	}
	
}
