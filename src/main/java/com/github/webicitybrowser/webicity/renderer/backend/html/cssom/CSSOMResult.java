package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.Set;

public interface CSSOMResult<T, U> {

	Set<CSSOMNode<T, U>> getMatchingNodes(T value);
	
}
