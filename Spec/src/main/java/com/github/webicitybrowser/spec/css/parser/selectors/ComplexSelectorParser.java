package com.github.webicitybrowser.spec.css.parser.selectors;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.parser.ParseFormatException;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.imp.TokenStreamImp;
import com.github.webicitybrowser.spec.css.parser.selectors.combinator.CombinatorParser;
import com.github.webicitybrowser.spec.css.parser.selectors.selector.AttributeSelectorParser;
import com.github.webicitybrowser.spec.css.parser.selectors.selector.ClassSelectorParser;
import com.github.webicitybrowser.spec.css.parser.selectors.selector.IDSelectorParser;
import com.github.webicitybrowser.spec.css.parser.selectors.selector.PsuedoSelectorParser;
import com.github.webicitybrowser.spec.css.parser.selectors.selector.TypeSelectorParser;
import com.github.webicitybrowser.spec.css.parser.tokens.ColonToken;
import com.github.webicitybrowser.spec.css.parser.tokens.CommaToken;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.EOFToken;
import com.github.webicitybrowser.spec.css.parser.tokens.HashToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.LSBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelector;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.spec.css.selectors.SelectorSpecificity;

public class ComplexSelectorParser {

	private final TypeSelectorParser typeSelectorParser = new TypeSelectorParser();
	private final ClassSelectorParser classSelectorParser = new ClassSelectorParser();
	private final IDSelectorParser idSelectorParser = new IDSelectorParser();
	private final AttributeSelectorParser attributeSelectorParser = new AttributeSelectorParser();
	private final PsuedoSelectorParser psuedoSelectorParser = new PsuedoSelectorParser();
	
	private final CombinatorParser combinatorParser = new CombinatorParser();
	
	public ComplexSelector[] parseMany(TokenLike[] prelude, int order) {
		TokenStream stream = new TokenStreamImp(prelude);
		List<ComplexSelector> selectors = new ArrayList<>();
		
		while (!(stream.peek() instanceof EOFToken)) {
			ComplexSelector complexSelector;
			try {
				complexSelector = consumeComplexSelector(stream, order);
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

	private ComplexSelector consumeComplexSelector(TokenStream stream, int order) throws ParseFormatException {
		List<ComplexSelectorPart> selectorParts = new ArrayList<>();
		
		consumeWhitespace(stream);
		
		int lastRunPosition = 0;
		while (!isSeperatingToken(stream.peek())) {
			if (lastRunPosition != 0) {
				while (stream.position() > lastRunPosition) {
					stream.unread();
				}
				selectorParts.add(combinatorParser.parse(stream));
			}
			consumeSimpleSelectors(stream, selectorParts);
			lastRunPosition = stream.position();
			consumeWhitespace(stream);
		}
		
		stream.read();
		
		return createComplexSelectorFromParts(selectorParts, order);
	}

	private void consumeSimpleSelectors(TokenStream stream, List<ComplexSelectorPart> selectorParts) throws ParseFormatException {
		ComplexSelectorPart selectorPart = consumeSimpleSelector(stream);
		selectorParts.add(selectorPart);

		while (isDelimiterToken(stream.peek(), '.') || stream.peek() instanceof LSBracketToken) {
			selectorPart = consumeSimpleSelector(stream);
			selectorParts.add(selectorPart);
		}
	}

	private ComplexSelectorPart consumeSimpleSelector(TokenStream stream) throws ParseFormatException {
		TokenLike token = stream.peek();
		if (token instanceof IdentToken) {
			return typeSelectorParser.parse(stream);
		} else if (isDelimiterToken(token, '.')) {
			return classSelectorParser.parse(stream);
		} else if (token instanceof HashToken) {
			return idSelectorParser.parse(stream);
		} else if (token instanceof LSBracketToken) {
			return attributeSelectorParser.parse(stream);
		} else if (token instanceof ColonToken) {
			return psuedoSelectorParser.parse(stream);
		} else {
			throw new ParseFormatException("Expected simple selector", stream.position());
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

	private ComplexSelector createComplexSelectorFromParts(List<ComplexSelectorPart> selectorParts, int order) {
		ComplexSelectorPart[] parts = selectorParts.toArray(ComplexSelectorPart[]::new);
		SelectorSpecificity selectorSpecificity = SelectorSpecificityCalculator.calculateSpecificity(parts, order);
		
		return new ComplexSelector() {	
			@Override
			public SelectorSpecificity getSpecificity() {
				return selectorSpecificity;
			}
			
			@Override
			public ComplexSelectorPart[] getParts() {
				return parts;
			}
		};
	}

}
