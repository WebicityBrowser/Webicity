package com.github.webicitybrowser.spiderhtml.misc;

import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.Token;

public final class EmitterLogic {

	private EmitterLogic() {}

	public static void emit(SharedContext sharedContext, InsertionContext insertionContext, Token token) {
		// TODO: Foreign elements
		sharedContext
			.getInsertionMode()
			.emit(sharedContext, insertionContext, token);
	}
	
}
