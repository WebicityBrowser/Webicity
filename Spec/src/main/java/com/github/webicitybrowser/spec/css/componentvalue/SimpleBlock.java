package com.github.webicitybrowser.spec.css.componentvalue;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;

public interface SimpleBlock extends ComponentValue {

	Token getType();
	
	TokenLike[] getValue();
	
}
