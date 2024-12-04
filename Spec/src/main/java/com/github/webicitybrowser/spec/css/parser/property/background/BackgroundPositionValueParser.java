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
import com.github.webicitybrowser.spec.css.property.background.BackgroundPositionLayersValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundPositionValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundPositionValue.BackgroundAxisPosition;
import com.github.webicitybrowser.spec.css.property.background.BackgroundPositionValue.BackgroundAxisReference;
import com.github.webicitybrowser.spec.css.property.shared.length.AbsoluteLengthValue;

public class BackgroundPositionValueParser implements PropertyValueParser<BackgroundPositionLayersValue> {

	private static final ListParser<BackgroundPositionValue> listParser = new ListParser<>(new BgPositionValueParser());

	@Override
	public PropertyValueParseResult<BackgroundPositionLayersValue> parse(TokenLike[] tokens, int offset, int length) {
		List<BackgroundPositionValue> result = listParser.parseList(tokens, offset, length);
		if (result.isEmpty()) return PropertyValueParseResultImp.empty();
		
		return PropertyValueParseResultImp.of(new BackgroundPositionLayersValue(result), result.size());
	}

	public static class BgPositionValueParser implements PropertyValueParser<BackgroundPositionValue> {

		private static final AbsoluteLengthValue ZERO_LENGTH = AbsoluteLengthValue.of(0, "px");
		private static final List<String> ALLOWED_HORIZONTAL_VALUES = List.of("left", "right", "center");
		private static final List<String> ALLOWED_VERTICAL_VALUES = List.of("top", "bottom", "center");

		private static final LengthPercentageValueParser lengthPercentageValueParser = new LengthPercentageValueParser();
	
		@Override
		public PropertyValueParseResult<BackgroundPositionValue> parse(TokenLike[] tokens, int offset, int length) {
			PropertyValueParseResult<BackgroundPositionValue> result = parseTwoKeywordPosition(tokens, offset, length);
			if (result.getResult().isPresent()) return result;

			result = parseTwoAnyTypePosition(tokens, offset, length);
			if (result.getResult().isPresent()) return result;
			
			result = parseOneKeywordPosition(tokens, offset, length);
			if (result.getResult().isPresent()) return result;

			result = parseOneRelativePosition(tokens, offset, length);
			return result;
		}

		private PropertyValueParseResult<BackgroundPositionValue> parseOneKeywordPosition(TokenLike[] tokens, int offset, int length) {
			if (length < 1 || !(tokens[offset] instanceof IdentToken identToken)) return PropertyValueParseResultImp.empty();
			
			String keyword = identToken.value();
			switch (keyword) {
				case "left":
					return PropertyValueParseResultImp.of(position(BackgroundAxisReference.LEFT, BackgroundAxisReference.CENTER), 1);
				case "right":
					return PropertyValueParseResultImp.of(position(BackgroundAxisReference.RIGHT, BackgroundAxisReference.CENTER), 1);
				case "center":
					return PropertyValueParseResultImp.of(position(BackgroundAxisReference.CENTER, BackgroundAxisReference.CENTER), 1);
				case "top":
					return PropertyValueParseResultImp.of(position(BackgroundAxisReference.CENTER, BackgroundAxisReference.TOP), 1);
				case "bottom":
					return PropertyValueParseResultImp.of(position(BackgroundAxisReference.CENTER, BackgroundAxisReference.BOTTOM), 1);
				default:
					return PropertyValueParseResultImp.empty();
			}
		}

		private PropertyValueParseResult<BackgroundPositionValue> parseOneRelativePosition(TokenLike[] tokens, int offset, int length) {
			PropertyValueParseResult<CSSValue> horizontalPositionResult = lengthPercentageValueParser.parse(tokens, offset, length);
			if (horizontalPositionResult.getResult().isEmpty()) return PropertyValueParseResultImp.empty();

			return PropertyValueParseResultImp.of(
				new BackgroundPositionValue(
					new BackgroundAxisPosition(BackgroundAxisReference.LEFT, horizontalPositionResult.getResult().get()),
					new BackgroundAxisPosition(BackgroundAxisReference.CENTER, ZERO_LENGTH)),
				horizontalPositionResult.getLength());
		}

		private PropertyValueParseResult<BackgroundPositionValue> parseTwoKeywordPosition(TokenLike[] tokens, int offset, int length) {
			if (length < 2) return PropertyValueParseResultImp.empty();

			if (!(tokens[offset] instanceof IdentToken identToken1)) return PropertyValueParseResultImp.empty();
			int totalLength = 1;
			PropertyValueParseResult<CSSValue> offsetResult1 = lengthPercentageValueParser.parse(tokens, offset + totalLength, length - totalLength);
			if (offsetResult1.getResult().isPresent()) totalLength += offsetResult1.getLength();

			if (!(
				offset + totalLength < length
				&& tokens[offset + totalLength] instanceof IdentToken identToken2)
			) {
				return PropertyValueParseResultImp.empty();
			}
			
			totalLength++;
			PropertyValueParseResult<CSSValue> offsetResult2 = lengthPercentageValueParser.parse(tokens, offset + totalLength, length - totalLength);
			if (offsetResult2.getResult().isPresent()) totalLength += offsetResult2.getLength();

			if (valuesAreReversed(identToken1, identToken2)) {
				IdentToken temp = identToken1;
				identToken1 = identToken2;
				identToken2 = temp;

				PropertyValueParseResult<CSSValue> tempOffset = offsetResult1;
				offsetResult1 = offsetResult2;
				offsetResult2 = tempOffset;
			}
			if (!valuesAllowed(identToken1, identToken2)) return PropertyValueParseResultImp.empty();

			BackgroundAxisReference horizontal = switch (identToken1.value()) {
				case "left" -> BackgroundAxisReference.LEFT;
				case "right" -> BackgroundAxisReference.RIGHT;
				default -> BackgroundAxisReference.CENTER;
			};
			CSSValue horizontalOffset = offsetResult1.getResult().orElse(ZERO_LENGTH);

			BackgroundAxisReference vertical = switch (identToken2.value()) {
				case "top" -> BackgroundAxisReference.TOP;
				case "bottom" -> BackgroundAxisReference.BOTTOM;
				default -> BackgroundAxisReference.CENTER;
			};
			CSSValue verticalOffset = offsetResult2.getResult().orElse(ZERO_LENGTH);

			return PropertyValueParseResultImp.of(
				new BackgroundPositionValue(
					new BackgroundAxisPosition(horizontal, horizontalOffset),
					new BackgroundAxisPosition(vertical, verticalOffset)),
				totalLength);
		}

		private PropertyValueParseResult<BackgroundPositionValue> parseTwoAnyTypePosition(TokenLike[] tokens, int offset, int length) {
			if (length < 2) return PropertyValueParseResultImp.empty();
			
			PropertyValueParseResult<BackgroundAxisPosition> horizontalPositionResult = parseAxisPosition(
				tokens, offset, length, ALLOWED_HORIZONTAL_VALUES, BackgroundAxisReference.LEFT);
			if (horizontalPositionResult.getResult().isEmpty()) return PropertyValueParseResultImp.empty();
			int totalLength = horizontalPositionResult.getLength();

			PropertyValueParseResult<BackgroundAxisPosition> verticalPositionResult = parseAxisPosition(
				tokens, offset + totalLength, length - totalLength, ALLOWED_VERTICAL_VALUES, BackgroundAxisReference.TOP);
			if (verticalPositionResult.getResult().isEmpty()) return PropertyValueParseResultImp.empty();
			totalLength += verticalPositionResult.getLength();

			return PropertyValueParseResultImp.of(
				new BackgroundPositionValue(horizontalPositionResult.getResult().get(), verticalPositionResult.getResult().get()),
				totalLength);
		}

		private PropertyValueParseResult<BackgroundAxisPosition> parseAxisPosition(
			TokenLike[] tokens, int offset, int length,
			List<String> allowedValues, BackgroundAxisReference defaultValue
		) {
			if (length < 1) return PropertyValueParseResultImp.empty();
			
			PropertyValueParseResult<CSSValue> valueResult = lengthPercentageValueParser.parse(tokens, offset, length);
			if (valueResult.getResult().isPresent()) return PropertyValueParseResultImp.of(
				new BackgroundAxisPosition(defaultValue, valueResult.getResult().get()),
				valueResult.getLength());
			
			if (!(tokens[offset] instanceof IdentToken identToken)) return PropertyValueParseResultImp.empty();
			if (!allowedValues.contains(identToken.value())) return PropertyValueParseResultImp.empty();

			return PropertyValueParseResultImp.of(
				new BackgroundAxisPosition(
					switch (identToken.value()) {
						case "left" -> BackgroundAxisReference.LEFT;
						case "right" -> BackgroundAxisReference.RIGHT;
						case "top" -> BackgroundAxisReference.TOP;
						case "bottom" -> BackgroundAxisReference.BOTTOM;
						default -> BackgroundAxisReference.CENTER;
					},
					ZERO_LENGTH),
				1);
		}

		private BackgroundPositionValue position(BackgroundAxisReference horizontal, BackgroundAxisReference vertical) {
			return new BackgroundPositionValue(
				new BackgroundAxisPosition(horizontal, ZERO_LENGTH),
				new BackgroundAxisPosition(vertical, ZERO_LENGTH));
		}

		private boolean valuesAreReversed(IdentToken identToken1, IdentToken identToken2) {
			return identToken1.value().equals("top") || identToken1.value().equals("bottom")
				|| identToken2.value().equals("left") || identToken2.value().equals("right");
		}

		private boolean valuesAllowed(IdentToken identToken1, IdentToken identToken2) {
			return ALLOWED_HORIZONTAL_VALUES.contains(identToken1.value()) && ALLOWED_VERTICAL_VALUES.contains(identToken2.value());
		}

	}
	
}
