package com.github.webicitybrowser.spec.html.parse;

import java.io.IOException;
import java.io.Reader;

import com.github.webicitybrowser.spec.html.parse.tree.HTMLTreeBuilder;

public interface HTMLParser {

	void parse(Reader inputReader, HTMLTreeBuilder treeBuilder) throws IOException;
	
}
