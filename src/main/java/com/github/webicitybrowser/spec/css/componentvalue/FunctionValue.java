package com.github.webicitybrowser.spec.css.componentvalue;

import com.github.webicitybrowser.spec.css.parser.TokenLike;

public interface FunctionValue extends ComponentValue {

	String getName();
	
	TokenLike[] getValue();

}
