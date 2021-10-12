package everyos.browser.spec.jcss.cssom;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.spec.jcss.cssom.selector.ComplexSelector;
import everyos.browser.spec.jcss.cssom.selector.ComplexSelectorPart;
import everyos.browser.spec.jcss.cssom.selector.combinator.ChildCombinator;
import everyos.browser.spec.jcss.cssom.selector.combinator.Combinator;
import everyos.browser.spec.jcss.cssom.selector.combinator.DescendantCombinator;
import everyos.browser.spec.jcss.cssom.selector.combinator.NextSiblingCombinator;
import everyos.browser.spec.jcss.cssom.selector.combinator.SubsequentSiblingCombinator;
import everyos.browser.spec.jcss.cssom.selector.other.GlobSelector;
import everyos.browser.spec.jcss.cssom.selector.psuedo.PsuedoElementSelector;
import everyos.browser.spec.jcss.cssom.selector.psuedo.PsuedoIdentitySelector;
import everyos.browser.spec.jcss.cssom.selector.simple.ClassSelector;
import everyos.browser.spec.jcss.cssom.selector.simple.IDSelector;
import everyos.browser.spec.jcss.cssom.selector.simple.TypeSelector;
import everyos.browser.spec.jcss.parser.CommaToken;
import everyos.browser.spec.jcss.parser.DelimToken;
import everyos.browser.spec.jcss.parser.HashToken;
import everyos.browser.spec.jcss.parser.IdentToken;
import everyos.browser.spec.jcss.parser.WhitespaceToken;

public class SelectorParser {
	public ComplexSelector parse(Object[] prelude, int[] offset) {
		List<ComplexSelectorPart> selectorParts = new ArrayList<>();
		PsuedoElementSelector psuedoElementSelector = new PsuedoIdentitySelector();
		
		int i = offset[0];
		
		State state = State.DEFAULT;
		boolean spaceEncountered = false;
		for (; i < prelude.length; i++) {
			Object part = prelude[i];
			
			if (part instanceof CommaToken) {
				break;
			}
			
			switch(state) {
				// TODO: Handle namespaces
				case DEFAULT:
					if (part instanceof WhitespaceToken) {
						
					} else {
						state = State.BEFORE_KEYWORD;
						i--;
					}
					break;
					
				case BEFORE_KEYWORD:
					//TODO: Handle colon token
					if (part instanceof WhitespaceToken) {
						
					} else if (part instanceof IdentToken) {
						spaceEncountered = insertDescendantCombinatorIfNeeded(selectorParts, spaceEncountered);
						selectorParts.add(new TypeSelector(((IdentToken) part).getValue()));
						state = State.AFTER_KEYWORD;
					} else if (part instanceof HashToken) {
						state = State.ID_SELECTOR;
						i--;
					} else if (part instanceof DelimToken && ((DelimToken) part).getValue().equals(".")) { //TODO
						state = State.CLASS_SELECTOR;
					} else if (isCharacterToken(part, '*')) {
						spaceEncountered = insertDescendantCombinatorIfNeeded(selectorParts, spaceEncountered);
						selectorParts.add(new GlobSelector());
						state = State.AFTER_KEYWORD;
					} else {
						// Not (currently) supported
						state = State.EARLY_RETURN;
					}
					break;
					
				case ID_SELECTOR:
					if (part instanceof HashToken) {
						spaceEncountered = insertDescendantCombinatorIfNeeded(selectorParts, spaceEncountered);
						selectorParts.add(new IDSelector(((HashToken) part).getValue()));
						state = State.AFTER_KEYWORD;
					} else {
						state = State.EARLY_RETURN;
					}
					break;
					
				case CLASS_SELECTOR:
					if (part instanceof IdentToken) {
						spaceEncountered = insertDescendantCombinatorIfNeeded(selectorParts, spaceEncountered);
						selectorParts.add(new ClassSelector(((IdentToken) part).getValue()));
						state = State.AFTER_KEYWORD;
					} else {
						state = State.EARLY_RETURN;
					}
					break;
					
				case AFTER_KEYWORD:
					if (part instanceof WhitespaceToken) {
						spaceEncountered = true;
					} else if (isCharacterToken(part, '>')) {
						selectorParts.add(new ChildCombinator());
						state = State.BEFORE_KEYWORD;
					} else if (isCharacterToken(part, '+')) {
						selectorParts.add(new NextSiblingCombinator());
						state = State.BEFORE_KEYWORD;
					} else if (isCharacterToken(part, '~')) {
						selectorParts.add(new SubsequentSiblingCombinator());
						state = State.BEFORE_KEYWORD;
					} else {
						state = State.BEFORE_KEYWORD;
						i--;
					}
					break;
					
				default:
					state = State.EARLY_RETURN;
					break;
			}
			
			if (state == State.EARLY_RETURN) {
				break;
			}
		}
		
		while (i < prelude.length && !(prelude[i] instanceof CommaToken)) {
			i++;
		}
		
		offset[0] = i + 1;
		
		if (state == State.AFTER_KEYWORD || state == State.FINISHED) {
			ComplexSelectorPart[] parts = selectorParts.toArray(new ComplexSelectorPart[selectorParts.size()]);
			return new ComplexSelector(parts, psuedoElementSelector);
		}
		return null;
	}
	
	private boolean insertDescendantCombinatorIfNeeded(List<ComplexSelectorPart> selectorParts, boolean spaceEncountered) {
		if (spaceEncountered && !(selectorParts.get(selectorParts.size()-1) instanceof Combinator)) {
			selectorParts.add(new DescendantCombinator());
		}
		
		return false;
	}
	
	private boolean isCharacterToken(Object token, char ch) {
		return token instanceof DelimToken && String.valueOf(ch).equals(((DelimToken) token).getValue());
	}
	
	private static enum State {
		DEFAULT, EARLY_RETURN, AFTER_KEYWORD, BEFORE_KEYWORD, ID_SELECTOR, CLASS_SELECTOR, FINISHED
	}
}
