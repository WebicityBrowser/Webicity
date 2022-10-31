package everyos.web.spec.css.parser.componentvalue;

import everyos.web.spec.css.parser.tokens.Token;

public interface SimpleBlock extends ComponentValue {

	Token getType();
	
	ComponentValue[] getValue();
	
}
