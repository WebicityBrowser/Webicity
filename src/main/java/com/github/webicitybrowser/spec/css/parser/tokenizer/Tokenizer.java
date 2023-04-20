package com.github.webicitybrowser.spec.css.parser.tokenizer;

import java.io.IOException;
import java.io.Reader;

import com.github.webicitybrowser.spec.css.parser.tokens.Token;

public interface Tokenizer {

	Token[] tokenize(Reader input) throws IOException;
	
}
