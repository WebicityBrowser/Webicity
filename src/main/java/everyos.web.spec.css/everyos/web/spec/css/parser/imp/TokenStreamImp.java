package everyos.web.spec.css.parser.imp;

import everyos.web.spec.css.parser.TokenLike;
import everyos.web.spec.css.parser.TokenStream;
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

	@Override
	public TokenLike peek() {
		if (position >= tokens.length) {
			return new EOFToken() {};
		}
		return tokens[position];
	}

	@Override
	public int position() {
		return position;
	}

}
