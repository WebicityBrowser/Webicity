package everyos.browser.jcss.parser;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.jcss.intf.CSSRule;

public class JCSSParser {
	public CSSRule[] parseAListOfRules(CSSToken[] tokens) {
		return consumeAListOfRules(tokens, false);
	}

	private CSSRule[] consumeAListOfRules(CSSToken[] tokens, boolean b) {
		List<CSSRule> rules = new ArrayList<>();
		TokenStream stream = new TokenStream(tokens);
		
		while (true) {
			CSSToken token = stream.read();
			
			if (token instanceof EOFToken) {
				return rules.toArray(new CSSRule[rules.size()]);
			}
		}
	}
	
	private static class TokenStream {
		private int i = 0;
		private CSSToken[] tokens;
		
		public TokenStream(CSSToken[] tokens) {
			this.tokens = tokens;
		}
		
		public CSSToken read() {
			return tokens[i++];
		}
		
		public void unread() {
			i--;
		}
	}
}
