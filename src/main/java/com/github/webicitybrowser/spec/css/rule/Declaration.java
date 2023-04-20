package com.github.webicitybrowser.spec.css.rule;

import com.github.webicitybrowser.spec.css.parser.TokenLike;

public interface Declaration extends CSSRule {

	String getName();

	TokenLike[] getValue();

	boolean isImportant();

}
