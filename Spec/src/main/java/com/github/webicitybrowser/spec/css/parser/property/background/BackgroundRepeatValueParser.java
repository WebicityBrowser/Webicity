package com.github.webicitybrowser.spec.css.parser.property.background;

import java.util.List;
import java.util.Map;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.property.shared.ListParser;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.background.BackgroundRepeatValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundRepeatValue.RepeatStyle;
import com.github.webicitybrowser.spec.css.property.background.BackgroundRepeatLayersValue;

public class BackgroundRepeatValueParser implements PropertyValueParser<BackgroundRepeatLayersValue> {

	private static final Map<String, RepeatStyle> repeatStyleMap = Map.of(
		"repeat", RepeatStyle.REPEAT,
		"space", RepeatStyle.SPACE,
		"round", RepeatStyle.ROUND,
		"no-repeat", RepeatStyle.NO_REPEAT
	);

	private static final ListParser<BackgroundRepeatValue> listParser = new ListParser<>(new BgRepeatValueParser());

	@Override
	public PropertyValueParseResult<BackgroundRepeatLayersValue> parse(TokenLike[] tokens, int offset, int length) {
		List<BackgroundRepeatValue> result = listParser.parseList(tokens, offset, length);
		if (result.isEmpty()) return PropertyValueParseResultImp.empty();

		return PropertyValueParseResultImp.of(new BackgroundRepeatLayersValue(result), result.size());
	}
	
	public static class BgRepeatValueParser implements PropertyValueParser<BackgroundRepeatValue> {
		
		@Override
		public PropertyValueParseResult<BackgroundRepeatValue> parse(TokenLike[] tokens, int offset, int length) {
			if (length < 1 || !(tokens[offset] instanceof IdentToken)) return PropertyValueParseResultImp.empty();

			IdentToken firstToken = (IdentToken) tokens[offset];
			if (isSingleComponent(firstToken)) {
				return firstToken.getValue().equals("repeat-x")
					? PropertyValueParseResultImp.of(new BackgroundRepeatValue(RepeatStyle.REPEAT, RepeatStyle.NO_REPEAT), 1)
					: PropertyValueParseResultImp.of(new BackgroundRepeatValue(RepeatStyle.NO_REPEAT, RepeatStyle.REPEAT), 1);
			}

			RepeatStyle firstStyle = repeatStyleMap.get(firstToken.getValue());
			if (firstStyle == null) return PropertyValueParseResultImp.empty();

			boolean hasTwoTokens = length >= 2 && tokens[offset + 1] instanceof IdentToken;
			if (hasTwoTokens && isSecondComponent((IdentToken) tokens[offset + 1])) {
				RepeatStyle secondStyle = repeatStyleMap.get(((IdentToken) tokens[offset + 1]).getValue());
				return PropertyValueParseResultImp.of(new BackgroundRepeatValue(firstStyle, secondStyle), 2);
			}
			
			return PropertyValueParseResultImp.of(new BackgroundRepeatValue(firstStyle, firstStyle), 1);
		}

		private boolean isSingleComponent(IdentToken firstToken) {
			return firstToken.getValue().equals("repeat-x")
				|| firstToken.getValue().equals("repeat-y");
		}

		private boolean isSecondComponent(IdentToken secondToken) {
			return repeatStyleMap.containsKey(secondToken.getValue());
		}
		
	}
	
}
