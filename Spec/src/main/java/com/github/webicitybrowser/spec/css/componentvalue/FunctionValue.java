package com.github.webicitybrowser.spec.css.componentvalue;

import com.github.webicitybrowser.spec.css.parser.TokenLike;

public record FunctionValue(String name, TokenLike[] value) implements ComponentValue {

}
