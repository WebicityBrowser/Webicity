package com.github.webicitybrowser.ecmaspiral.parser.tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import com.github.webicitybrowser.ecmaspiral.parser.exception.ParseException;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.Token;

public interface Tokenizer {
	
	List<Token> tokenize(Reader inputReader) throws IOException, ParseException;

}
