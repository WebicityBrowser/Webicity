package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp.CSSOMTreeImp;

public interface CSSOMTree<T, U> {
	
	void apply(T participant, CSSOMParticipantTraverser<T, U> traverser);

	public static <T, U> CSSOMTree<T, U> create(CSSOMNode<T, U> rootNode) {
		return new CSSOMTreeImp<T, U>(rootNode);
	}
	
}
