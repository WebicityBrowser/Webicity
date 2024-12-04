package com.github.webicitybrowser.spec.css.componentvalue;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;

public record SimpleBlock(Token type, List<TokenLike> value) implements ComponentValue {
	
}
