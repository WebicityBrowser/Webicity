package com.github.webicitybrowser.spiderhtml.tokenize;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;

public interface TokenizeState {

	void process(SharedContext context, ParsingContext parsingContext, int ch);

}
