package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.List;

public interface CSSOMResult<T, U> {

	List<CSSOMNode<T, U>> getMatchingNodes(T value);
	
}
