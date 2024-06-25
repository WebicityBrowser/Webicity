package com.github.webicitybrowser.spec.css.rule;

import com.github.webicitybrowser.spec.css.componentvalue.SimpleBlock;
import com.github.webicitybrowser.spec.css.parser.TokenLike;

public interface QualifiedRule extends CSSRule {

	TokenLike[] getPrelude();
	
	SimpleBlock getValue();
	
}
