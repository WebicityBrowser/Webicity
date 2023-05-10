package com.github.webicitybrowser.spec.css.parser.selectors;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.parser.ParseFormatException;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.imp.TokenStreamImp;
import com.github.webicitybrowser.spec.css.parser.selectors.selector.ClassSelectorParser;
import com.github.webicitybrowser.spec.css.parser.selectors.selector.TypeSelectorParser;
import com.github.webicitybrowser.spec.css.parser.tokens.CommaToken;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.EOFToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelector;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;

public class ComplexSelectorParser {

	private final TypeSelectorParser typeSelectorParser = new TypeSelectorParser();
	private final ClassSelectorParser classSelectorParser = new ClassSelectorParser();
	
	public ComplexSelector[] parseMany(TokenLike[] prelude) {
		TokenStream stream = new TokenStreamImp(prelude);
		List<ComplexSelector> selectors = new ArrayList<>();
		
		while (!(stream.peek() instanceof EOFToken)) {
			ComplexSelector complexSelector;
			try {
				complexSelector = consumeComplexSelector(stream);
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

	private ComplexSelector consumeComplexSelector(TokenStream stream) throws ParseFormatException {
		List<ComplexSelectorPart> selectorParts = new ArrayList<>();
		
		consumeWhitespace(stream);
		
		ComplexSelectorPart selectorPart = consumeSimpleSelector(stream);
		if (selectorPart == null) {
			return null; // Bad selector
		}
		selectorParts.add(selectorPart);
		
		consumeWhitespace(stream);
		
		TokenLike token = stream.peek();
		if (!isSeperatingToken(token)) {
			return null;
		}
		stream.read();
		
		return createComplexSelectorFromParts(selectorParts);
	}

	private ComplexSelectorPart consumeSimpleSelector(TokenStream stream) throws ParseFormatException {
		TokenLike token = stream.peek();
		if (token instanceof IdentToken identToken) {
			return typeSelectorParser.parse(stream);
		} else if (isDelimiterToken(token, '.')) {
			return classSelectorParser.parse(stream);
		} else {
			return null;
		}
	}

	private void consumeWhitespace(TokenStream stream) {
		while (stream.peek() instanceof WhitespaceToken) {
			stream.read();
		}
	}

	private boolean isSeperatingToken(TokenLike token) {
		return token instanceof CommaToken || token instanceof EOFToken;
	}
	
	private boolean isDelimiterToken(TokenLike token, int ch) {
		return
			token instanceof DelimToken delimToken &&
			delimToken.getValue() == ch;
	}
	
	private void throwOutBadRule(TokenStream stream) {
		while (!isSeperatingToken(stream.read()));
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
