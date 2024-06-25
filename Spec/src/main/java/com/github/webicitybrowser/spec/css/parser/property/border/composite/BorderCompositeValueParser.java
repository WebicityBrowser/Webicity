package com.github.webicitybrowser.spec.css.parser.property.border.composite;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.border.color.BorderColorLonghandValueParser;
import com.github.webicitybrowser.spec.css.parser.property.border.style.BorderStyleLonghandValueParser;
import com.github.webicitybrowser.spec.css.parser.property.border.width.BorderWidthLonghandValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.property.shared.anyorder.AnyOrderParser;
import com.github.webicitybrowser.spec.css.parser.property.shared.anyorder.AnyOrderParser.AnyOrderParserResult;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.border.BorderCompositeValue;
import com.github.webicitybrowser.spec.css.property.border.BorderStyleValue;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public class BorderCompositeValueParser implements PropertyValueParser<BorderCompositeValue> {

	private final AnyOrderParser anyOrderParser = new AnyOrderParser(List.of(
		new BorderWidthLonghandValueParser(),
		new BorderStyleLonghandValueParser(),
		new BorderColorLonghandValueParser()
	));

	@Override
	public PropertyValueParseResult<BorderCompositeValue> parse(TokenLike[] tokens, int offset, int length) {
		PropertyValueParseResult<AnyOrderParserResult> anyOrderParserResult = anyOrderParser.parse(tokens, offset, length);
		if (anyOrderParserResult.getResult().isEmpty()) {
			return PropertyValueParseResultImp.empty();
		}
		
		List<CSSValue> results = anyOrderParserResult.getResult().get().results();

		CSSValue width = results.get(0);
		BorderStyleValue style = (BorderStyleValue) results.get(1);
		ColorValue color = (ColorValue) results.get(2);

		if (width == null && style == null && color == null) {
			return PropertyValueParseResultImp.empty();
		}

		return PropertyValueParseResultImp.of(
			new BorderCompositeValue(width, style, color),
			anyOrderParserResult.getLength()
		);
	}
	
}
