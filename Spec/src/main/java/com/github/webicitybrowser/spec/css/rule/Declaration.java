package com.github.webicitybrowser.spec.css.rule;

import com.github.webicitybrowser.spec.css.parser.TokenLike;

public record Declaration(String name, TokenLike[] value, boolean isImportant) implements CSSRule {

}
