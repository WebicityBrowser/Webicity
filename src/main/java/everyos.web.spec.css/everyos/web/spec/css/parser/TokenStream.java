package everyos.web.spec.css.parser;

public interface TokenStream {

	TokenLike read();

	void unread();
	
}
