package everyos.web.spec.idl.parser;

import everyos.web.spec.idl.parser.tokens.EOFToken;
import everyos.web.spec.idl.parser.tokens.Token;

public class TokenStreamImp implements TokenStream {

	private final Token[] tokens;
	
	private int position;

	public TokenStreamImp(Token[] tokens) {
		this.tokens = tokens;
		this.position = 0;
	}

	@Override
	public Token read() {
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
