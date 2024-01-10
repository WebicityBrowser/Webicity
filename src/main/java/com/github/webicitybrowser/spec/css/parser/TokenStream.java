package com.github.webicitybrowser.spec.css.parser;

import com.github.webicitybrowser.spec.css.parser.imp.TokenStreamImp;

public interface TokenStream {

	TokenLike read();
	
	TokenLike peek();

	void unread();

	int position();

	boolean isEmpty();

	static TokenStream create(TokenLike[] tokens) {
		return new TokenStreamImp(tokens);
	}
	
}
