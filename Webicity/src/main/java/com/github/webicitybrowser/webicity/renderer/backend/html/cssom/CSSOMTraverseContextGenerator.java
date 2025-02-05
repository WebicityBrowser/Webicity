package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp.CSSOMTraverseContextGeneratorImp;

public interface CSSOMTraverseContextGenerator<T, U> {

	CSSOMTraverseContext<T, U> apply(T participant, CSSOMParticipantTraverser<T, U> traverser);

	public static <T, U> CSSOMTraverseContextGenerator<T, U> create() {
		return new CSSOMTraverseContextGeneratorImp<>();
	}
	
}
