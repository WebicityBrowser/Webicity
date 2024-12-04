package com.github.webicitybrowser.spec.css.rule;

import java.util.List;

import com.github.webicitybrowser.spec.css.componentvalue.SimpleBlock;
import com.github.webicitybrowser.spec.css.parser.TokenLike;

public record AtRule(String name, List<TokenLike> prelude, SimpleBlock value) implements CSSRule {
	
}
