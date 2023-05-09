package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.HashSet;
import java.util.Set;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;

public class CSSOMParticipantTracker<T, U> {

	private Set<CSSOMNode<T, U>> matchingNodes = new HashSet<>(1);

	public Set<CSSOMNode<T, U>> getMatchingNodes() {
		return matchingNodes;
	}

	public boolean addMatchingNode(CSSOMNode<T, U> node) {
		return matchingNodes.add(node);
	}
	
}
