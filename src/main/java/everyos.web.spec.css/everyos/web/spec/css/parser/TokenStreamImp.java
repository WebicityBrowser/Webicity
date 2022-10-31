package everyos.web.spec.css.parser;

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
			// This will hopefully be an EOFToken
			return tokens[tokens.length - 1];
		}
		return tokens[position++];
	}

	@Override
	public void unread() {
		position--;
	}

}
