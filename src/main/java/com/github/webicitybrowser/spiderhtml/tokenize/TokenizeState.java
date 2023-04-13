package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;

public interface TokenizeState {

	void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException;

}
