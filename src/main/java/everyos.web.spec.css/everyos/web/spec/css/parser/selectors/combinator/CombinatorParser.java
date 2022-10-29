package everyos.web.spec.css.parser.selectors.combinator;

import everyos.web.spec.css.parser.ParseFormatException;
import everyos.web.spec.css.parser.selectors.SelectorParser;
import everyos.web.spec.css.parser.tokens.DelimToken;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.parser.tokens.WhitespaceToken;
import everyos.web.spec.css.selectors.combinator.ChildCombinator;
import everyos.web.spec.css.selectors.combinator.Combinator;
import everyos.web.spec.css.selectors.combinator.DescendantCombinator;
import everyos.web.spec.css.selectors.combinator.NextSiblingCombinator;
import everyos.web.spec.css.selectors.combinator.SubsequentSiblingCombinator;

public class CombinatorParser implements SelectorParser {

	@Override
	public Combinator parse(Token[] tokens, int offset, int length) throws ParseFormatException {
		checkSelectorFormat(tokens, offset, length);
		
		int importantTokenLocation = getImportantTokenLocation(tokens, offset, length);
		return chooseCombinator(tokens[importantTokenLocation], importantTokenLocation);
	}

	private int getImportantTokenLocation(Token[] tokens, int offset, int length) throws ParseFormatException {
		int found = -1;
		for (int i = offset; i < offset + length; i++) {
			if (tokens[i] instanceof WhitespaceToken) {
				continue;
			}
			if (found != -1) {
				throw new ParseFormatException("Invalid combinator format", i);
			}
			found = i;
		}
		return Math.max(offset, found);
	}

	private void checkSelectorFormat(Token[] tokens, int offset, int length) throws ParseFormatException {
		if (length == 0) {
			throw new ParseFormatException("Invalid combinator format", offset);
		}
	}
	
	private Combinator chooseCombinator(Token token, int offset) throws ParseFormatException {
		if (token instanceof WhitespaceToken) {
			return createDescendantCombinator();
		} else if (isCharacterToken(token, '>')) {
			return createChildCombinator();
		} else if (isCharacterToken(token, '+')) {
			return createNextSiblingCombinator();
		} else if (isCharacterToken(token, '~')) {
			return createSubsequentSiblingCombinator();
		}
		throw new ParseFormatException("Invalid combinator format", offset);
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
	
	private boolean isCharacterToken(Token token, char ch) {
		return
			token instanceof DelimToken &&
			ch == ((DelimToken) token).getValue();
	}
	
}
