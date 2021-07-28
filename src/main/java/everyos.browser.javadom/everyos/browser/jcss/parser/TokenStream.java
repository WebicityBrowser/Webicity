package everyos.browser.jcss.parser;

public class TokenStream {
	private int i = 0;
	private CSSToken[] tokens;
	
	public TokenStream(CSSToken[] tokens) {
		this.tokens = tokens;
	}
	
	public CSSToken read() {
		return tokens[i++];
	}
	
	public CSSToken peek() {
		return tokens[i];
	}
	
	public void unread() {
		i--;
	}
}
