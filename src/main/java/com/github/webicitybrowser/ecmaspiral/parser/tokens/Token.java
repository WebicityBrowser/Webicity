package com.github.webicitybrowser.ecmaspiral.parser.tokens;

public interface Token {

	TokenMeta meta();
	
	record TokenMeta(int line, int column) {}

}
