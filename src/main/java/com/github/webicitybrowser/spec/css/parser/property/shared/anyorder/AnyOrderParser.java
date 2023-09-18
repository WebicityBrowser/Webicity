package com.github.webicitybrowser.spec.css.parser.property.shared.anyorder;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.property.shared.anyorder.AnyOrderParser.AnyOrderParserResult;
import com.github.webicitybrowser.spec.css.property.CSSValue;

public class AnyOrderParser implements PropertyValueParser<AnyOrderParserResult> {

	private final List<PropertyValueParser<? extends CSSValue>> parsers;

	public AnyOrderParser(List<PropertyValueParser<? extends CSSValue>> parsers) {
		this.parsers = parsers;
	}

	@Override
	public PropertyValueParseResult<AnyOrderParserResult> parse(TokenLike[] tokens, int offset, int length) {
		List<CSSValue> results = new ArrayList<>(parsers.size());
		for (int i = 0; i < parsers.size(); i++) {
			results.add(null);
		}
	
		int i = 0;
		while (i < length) {
			boolean found = false;
			for (int j = 0; j < parsers.size(); j++) {
				if (results.get(j) != null) continue;
				PropertyValueParser<? extends CSSValue> parser = parsers.get(j);
				PropertyValueParseResult<? extends CSSValue> result = parser.parse(tokens, offset + i, length - i);
				if (result.getResult().isEmpty()) continue;
				results.set(j, result.getResult().get());
				i += result.getLength();
				found = true;
				break;
			}
			if (!found) break;
		}

		return PropertyValueParseResultImp.of(new AnyOrderParserResult(results), i);
	}

	public static record AnyOrderParserResult(List<CSSValue> results) implements CSSValue {}

}
