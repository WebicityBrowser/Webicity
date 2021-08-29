package everyos.browser.jcss.cssom;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.jcss.cssom.selector.TypeSelectorPart;
import everyos.browser.jcss.parser.CommaToken;
import everyos.browser.jcss.parser.IdentToken;
import everyos.browser.jcss.parser.WhitespaceToken;

public class SelectorParser {
	public SelectorPart[] parse(Object[] prelude, int[] offset) {
		List<SelectorPart> selectorParts = new ArrayList<>();
		
		int i = offset[0];
		
		State state = State.DEFAULT;
		for (; i < prelude.length; i++) {
			Object part = prelude[offset[0]];
			
			/*System.out.print("A");
			System.out.println(part);*/
			
			if (state == State.EARLY_RETURN) {
				//TODO: Read up to comma
			}
			
			if (part instanceof CommaToken) {
				i++;
				break;
			}
			
			if (part instanceof WhitespaceToken) {
				continue;
			}
			
			switch(state) {
				case DEFAULT:
					if (part instanceof IdentToken) {
						i--;
						state = State.TYPE_SELECTOR;
					} else {
						state = State.EARLY_RETURN;
					}
					break;
					
				case TYPE_SELECTOR:
					if (part instanceof IdentToken) {
						selectorParts.add(new TypeSelectorPart(((IdentToken) part).getValue()));
						state = State.DEFAULT;
					} else {
						state = State.EARLY_RETURN;
					}
					break;
					
				default:
			}
		}
		
		offset[0] = i;
		
		if (state == State.DEFAULT) {
			return selectorParts.toArray(new SelectorPart[selectorParts.size()]);
		}
		return null;
	}
	
	private enum State {
		DEFAULT, TYPE_SELECTOR, EARLY_RETURN
	}
}
