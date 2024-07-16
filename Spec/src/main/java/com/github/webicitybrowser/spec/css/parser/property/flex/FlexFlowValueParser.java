package com.github.webicitybrowser.spec.css.parser.property.flex;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.property.shared.anyorder.AnyOrderParser;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexDirectionValue;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexFlowValue;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexWrapValue;

public class FlexFlowValueParser implements PropertyValueParser<FlexFlowValue> {

	private final AnyOrderParser anyOrderParser = new AnyOrderParser(List.of(
		new FlexDirectionValueParser(),
		new FlexWrapValueParser()
	));

	@Override
	public PropertyValueParseResult<FlexFlowValue> parse(TokenLike[] tokens, int offset, int length) {
		PropertyValueParseResult<AnyOrderParser.AnyOrderParserResult> anyOrderParserResult = anyOrderParser.parse(tokens, offset, length);
		if (anyOrderParserResult.getResult().isEmpty()) {
			return PropertyValueParseResultImp.empty();
		}
		
		List<?> results = anyOrderParserResult.getResult().get().results();

		FlexDirectionValue flexDirection = (FlexDirectionValue) results.get(0);
		FlexWrapValue flexWrap = (FlexWrapValue) results.get(1);

		if (flexDirection == null && flexWrap == null) {
			return PropertyValueParseResultImp.empty();
		}

		return PropertyValueParseResultImp.of(
			new FlexFlowValue(flexDirection, flexWrap),
			anyOrderParserResult.getLength()
		);
	}
	
}
