package everyos.web.spec.css.componentvalue;

import everyos.web.spec.css.parser.TokenLike;
import everyos.web.spec.css.parser.tokens.Token;

public interface SimpleBlock extends ComponentValue {

	Token getType();
	
	TokenLike[] getValue();
	
}
