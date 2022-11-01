package everyos.web.spec.css.parser;

import everyos.web.spec.css.parser.tokens.EOFToken;

public class TokenStreamImp implements TokenStream {

	private final TokenLike[] tokens;
	
	private int position;

	public TokenStreamImp(TokenLike[] tokens) {
		this.tokens = tokens;
		this.position = 0;
	}

	@Override
	public TokenLike read() {
		if (position >= tokens.length) {
			return new EOFToken() {};
		}
		return tokens[position++];
	}

	@Override
	public void unread() {
		position--;
	}

}
