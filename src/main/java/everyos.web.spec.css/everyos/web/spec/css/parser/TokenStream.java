package everyos.web.spec.css.parser;

public interface TokenStream {

	TokenLike read();
	
	TokenLike peek();

	void unread();

	int position();
	
}
