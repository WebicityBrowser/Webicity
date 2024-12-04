package com.github.webicitybrowser.spec.css.parser.property.shared;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.CSSValue;

public class EnumParser<T extends CSSValue> implements PropertyValueParser<T> {

	private final String[] names;
	private final T[] values;

	public EnumParser(String[] names, T[] values) {
		this.names = names;
		this.values = values;
	}

	@Override
	public PropertyValueParseResult<T> parse(TokenLike[] tokens, int offset, int length) {
		if (length < 1 || !(tokens[offset] instanceof IdentToken)) {
			return PropertyValueParseResultImp.empty();
		}

		String name = ((IdentToken) tokens[offset]).value();
		for (int i = 0; i < names.length; i++) {
			if (names[i].equals(name)) {
				return PropertyValueParseResultImp.of(values[i], 1);
			}
		}

		return PropertyValueParseResultImp.empty();
	}
	
}
