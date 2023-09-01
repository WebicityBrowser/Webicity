package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.Collection;

public interface CSSOMParticipantTraverser<T, U> {

	T[] getChildren(T participant);

	T getParent(T participant);

	 Collection<CSSOMNode<T, U>> getMatchingNodes(T participant);

	void addMatchingNode(T participant, CSSOMNode<T, U> baseNode);

}
