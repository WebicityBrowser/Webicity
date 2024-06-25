package com.github.webicitybrowser.spec.css.rule;

import com.github.webicitybrowser.spec.css.componentvalue.SimpleBlock;
import com.github.webicitybrowser.spec.css.parser.TokenLike;

public interface AtRule extends CSSRule {

	String getName();
	
	TokenLike[] getPrelude();
	
	SimpleBlock getValue();
	
}
