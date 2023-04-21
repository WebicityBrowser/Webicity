package com.github.webicitybrowser.spec.css.parser.selectors;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.parser.ParseFormatException;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.imp.TokenStreamImp;
import com.github.webicitybrowser.spec.css.parser.selectors.selector.TypeSelectorParser;
import com.github.webicitybrowser.spec.css.parser.tokens.CommaToken;
import com.github.webicitybrowser.spec.css.parser.tokens.EOFToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelector;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;

public class ComplexSelectorParser {

	private final TypeSelectorParser typeSelectorParser = new TypeSelectorParser();
	
	public ComplexSelector[] parseMany(TokenLike[] prelude) {
		TokenStream stream = new TokenStreamImp(prelude);
		List<ComplexSelector> selectors = new ArrayList<>();
		
		while (!(stream.peek() instanceof EOFToken)) {
			ComplexSelector complexSelector;
			try {
				complexSelector = consumeSelector(stream);
				if (complexSelector != null) {
					selectors.add(complexSelector);
				} else {
					throwOutBadRule(stream);
				}
			} catch (ParseFormatException e) {
				throwOutBadRule(stream);
			}
		}
		
		return selectors.toArray(ComplexSelector[]::new);
	}

	private ComplexSelector consumeSelector(TokenStream stream) throws ParseFormatException {
		List<ComplexSelectorPart> selectorParts = new ArrayList<>();
		
		consumeWhitespace(stream);
		
		TokenLike token = stream.peek();
		if (token instanceof IdentToken identToken) {
			selectorParts.add(typeSelectorParser.parse(stream));
		} else {
			return null;
		}
		
		consumeWhitespace(stream);
		
		token = stream.peek();
		if (!isDelimiterToken(token)) {
			return null;
		}
		stream.read();
		
		return createComplexSelectorFromParts(selectorParts);
	}

	private void consumeWhitespace(TokenStream stream) {
		while (stream.peek() instanceof WhitespaceToken) {
			stream.read();
		}
	}

	private boolean isDelimiterToken(TokenLike token) {
		return token instanceof CommaToken || token instanceof EOFToken;
	}
	
	private void throwOutBadRule(TokenStream stream) {
		while (!isDelimiterToken(stream.read()));
	}

	private ComplexSelector createComplexSelectorFromParts(List<ComplexSelectorPart> selectorParts) {
		ComplexSelectorPart[] parts = selectorParts.toArray(ComplexSelectorPart[]::new);
		return new ComplexSelector() {	
			@Override
			public int getSpecificity() {
				return 0;
			}
			
			@Override
			public ComplexSelectorPart[] getParts() {
				return parts;
			}
		};
	}

}
