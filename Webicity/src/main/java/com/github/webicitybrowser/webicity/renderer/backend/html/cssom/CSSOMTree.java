package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp.CSSOMTreeImp;

public interface CSSOMTree<T, U> {
	
	void apply(CSSOMTraverseContext<T, U> traverseContext);

	public static <T, U> CSSOMTree<T, U> create(CSSOMNode<T, U> rootNode) {
		return new CSSOMTreeImp<T, U>(rootNode);
	}
	
}
