package everyos.web.spec.css.parser.selectors.combinator;

import everyos.web.spec.css.parser.ParseFormatException;
import everyos.web.spec.css.parser.TokenLike;
import everyos.web.spec.css.parser.TokenStream;
import everyos.web.spec.css.parser.selectors.SelectorParser;
import everyos.web.spec.css.parser.tokens.DelimToken;
import everyos.web.spec.css.parser.tokens.WhitespaceToken;
import everyos.web.spec.css.selectors.combinator.ChildCombinator;
import everyos.web.spec.css.selectors.combinator.Combinator;
import everyos.web.spec.css.selectors.combinator.DescendantCombinator;
import everyos.web.spec.css.selectors.combinator.NextSiblingCombinator;
import everyos.web.spec.css.selectors.combinator.SubsequentSiblingCombinator;

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
			return createChildCombinator();
		} else if (isCharacterToken(token, '+')) {
			return createNextSiblingCombinator();
		} else if (isCharacterToken(token, '~')) {
			return createSubsequentSiblingCombinator();
		} else if (containsWhitespace) {
			stream.unread();
			return createDescendantCombinator();
		}
		throw new ParseFormatException("Invalid combinator format", stream.position());
	}

	private Combinator createDescendantCombinator() {
		return new DescendantCombinator() {};
	}
	
	private Combinator createChildCombinator() {
		return new ChildCombinator() {};
	}
	
	private Combinator createNextSiblingCombinator() {
		return new NextSiblingCombinator() {};
	}
	
	private Combinator createSubsequentSiblingCombinator() {
		return new SubsequentSiblingCombinator() {};
	}
	
	private boolean isCharacterToken(TokenLike token, char ch) {
		return
			token instanceof DelimToken &&
			ch == ((DelimToken) token).getValue();
	}
	
}
