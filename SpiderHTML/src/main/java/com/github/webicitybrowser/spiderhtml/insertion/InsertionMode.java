package com.github.webicitybrowser.spiderhtml.insertion;

import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.Token;

public interface InsertionMode {

	void emit(SharedContext context, InsertionContext insertionContext, Token token);

}
