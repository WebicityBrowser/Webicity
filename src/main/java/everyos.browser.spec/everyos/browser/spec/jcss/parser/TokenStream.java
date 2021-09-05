package everyos.browser.spec.jcss.parser;

public class TokenStream {
	private int i = 0;
	private CSSToken[] tokens;
	
	public TokenStream(CSSToken[] tokens) {
		this.tokens = tokens;
	}
	
	public CSSToken read() {
		if (i>=tokens.length) {
			return new EOFToken();
		}
		return tokens[i++];
	}
	
	public CSSToken peek() {
		if (i>=tokens.length) {
			return new EOFToken();
		}
		return tokens[i];
	}
	
	public void unread() {
		i--;
	}
}
