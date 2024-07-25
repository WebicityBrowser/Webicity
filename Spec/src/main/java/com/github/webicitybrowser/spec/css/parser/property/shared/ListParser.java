package com.github.webicitybrowser.spec.css.parser.property.shared;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.tokens.CommaToken;
import com.github.webicitybrowser.spec.css.property.CSSValue;

public class ListParser<T extends CSSValue> {

	private final PropertyValueParser<T> innerParser;

	public ListParser(PropertyValueParser<T> innerParser) {
		this.innerParser = innerParser;
	}

	public List<T> parseList(TokenLike[] tokens, int offset, int length) {
		List<T> results = new ArrayList<>();
		
		int current = offset;
		while (current < length) {
			int innerLength = determineInnerLength(tokens, current, length);
			PropertyValueParseResult<T> result = innerParser.parse(tokens, current, innerLength);
			if (result.getResult().isEmpty()) return List.of();
			results.add(result.getResult().get());
			current += innerLength + 1;
		}

		return results;
	}

	private int determineInnerLength(TokenLike[] tokens, int current, int length) {
		for (int i = current; i < length; i++) {
			if (tokens[i] instanceof CommaToken) {
				return i - current;
			}
		}
		return length - current;
	}
	
}
