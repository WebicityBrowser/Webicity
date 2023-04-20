package com.github.webicitybrowser.spec.css.parser;

public interface TokenStream {

	TokenLike read();
	
	TokenLike peek();

	void unread();

	int position();
	
}
