package com.github.webicitybrowser.spec.css.parser.tokenizer;

import java.io.IOException;
import java.io.Reader;

import com.github.webicitybrowser.spec.css.parser.tokenizer.imp.TokenizerImp;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;

public interface CSSTokenizer {

	Token[] tokenize(Reader input) throws IOException;
	
	public static CSSTokenizer create() {
		return new TokenizerImp();
	}
	
}
