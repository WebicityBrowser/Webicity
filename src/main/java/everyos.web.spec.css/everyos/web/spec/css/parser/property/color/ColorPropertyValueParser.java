package everyos.web.spec.css.parser.property.color;

import everyos.web.spec.css.parser.TokenLike;
import everyos.web.spec.css.parser.property.PropertyValueParseResult;
import everyos.web.spec.css.parser.property.PropertyValueParser;
import everyos.web.spec.css.parser.property.PropertyValueParserUtil;
import everyos.web.spec.css.property.color.ColorValue;

public class ColorPropertyValueParser implements PropertyValueParser<ColorValue> {
	
	private final PropertyValueParser<ColorValue> hexColorValueParser = new HexColorValueParser();
	private final PropertyValueParser<ColorValue> absoluteColorValueParser = new AbsoluteColorValueParser();
	private final PropertyValueParser<ColorValue> namedColorValueParser = new NamedColorValueParser();
	private final PropertyValueParser<ColorValue> transparentColorValueParser = new TransparentColorValueParser();

	@Override
	public PropertyValueParseResult<ColorValue> parse(TokenLike[] tokens, int offset, int length) {
		return PropertyValueParserUtil.takeLongestResult(
			hexColorValueParser.parse(tokens, offset, length),
			absoluteColorValueParser.parse(tokens, offset, length),
			namedColorValueParser.parse(tokens, offset, length),
			transparentColorValueParser.parse(tokens, offset, length));
	}

}
