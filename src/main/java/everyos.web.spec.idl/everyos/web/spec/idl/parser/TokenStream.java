package everyos.web.spec.idl.parser;

import everyos.web.spec.idl.parser.tokens.Token;

public interface TokenStream {

	Token read();

	void unread();
	
}
