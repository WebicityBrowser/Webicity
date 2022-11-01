package everyos.web.spec.css.componentvalue;

import everyos.web.spec.css.parser.TokenLike;

public interface FunctionValue extends ComponentValue {

	String getName();
	
	TokenLike[] getValue();

}
