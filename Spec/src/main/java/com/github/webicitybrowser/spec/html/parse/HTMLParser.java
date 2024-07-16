package com.github.webicitybrowser.spec.html.parse;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public interface HTMLParser {

	void parse(InputStream inputStream, HTMLTreeBuilder treeBuilder, ParserSettings parserSettings) throws IOException;

	void parse(Reader inputReader, HTMLTreeBuilder treeBuilder, ParserSettings parserSettings) throws IOException;
	
}
