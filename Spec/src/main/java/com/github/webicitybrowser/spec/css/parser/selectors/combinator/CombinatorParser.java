package com.github.webicitybrowser.spec.css.parser.selectors.combinator;

import com.github.webicitybrowser.spec.css.parser.ParseFormatException;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.selectors.SelectorParser;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;
import com.github.webicitybrowser.spec.css.selectors.combinator.ChildCombinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.Combinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.DescendantCombinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.NextSiblingCombinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.SubsequentSiblingCombinator;

public class CombinatorParser implements SelectorParser {

	@Override
	public Combinator parse(TokenStream stream) throws ParseFormatException {
		boolean containsWhitespace = false;
		if (stream.peek() instanceof WhitespaceToken) {
			containsWhitespace = true;
			consumeWhitespace(stream);
		}
		Combinator combinator = chooseCombinator(stream, containsWhitespace);
		consumeWhitespace(stream);
		
		return combinator;
	}

	private void consumeWhitespace(TokenStream stream) {
		while (stream.peek() instanceof WhitespaceToken) {
			stream.read();
		}
	}

	private Combinator chooseCombinator(TokenStream stream, boolean containsWhitespace) throws ParseFormatException {
		TokenLike token = stream.read();
		if (isCharacterToken(token, '>')) {
			return new ChildCombinator();
		} else if (isCharacterToken(token, '+')) {
			return new NextSiblingCombinator();
		} else if (isCharacterToken(token, '~')) {
			return new SubsequentSiblingCombinator();
		} else if (containsWhitespace) {
			stream.unread();
			return new DescendantCombinator();
		}
		throw new ParseFormatException("Invalid combinator format", stream.position());
	}
	
	private boolean isCharacterToken(TokenLike token, char ch) {
		return
			token instanceof DelimToken &&
			ch == ((DelimToken) token).value();
	}
	
}
