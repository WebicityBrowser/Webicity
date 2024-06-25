package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;

public class CSSOMParticipantTracker<T, U> {

	private Set<CSSOMNode<T, U>> matchingNodes = new TreeSet<>(new NodeComparator());

	public Set<CSSOMNode<T, U>> getMatchingNodes() {
		return matchingNodes;
	}

	public boolean addMatchingNode(CSSOMNode<T, U> node) {
		return matchingNodes.add(node);
	}
	
	private class NodeComparator implements Comparator<CSSOMNode<T, U>> {
		@Override
		public int compare(CSSOMNode<T, U> o1, CSSOMNode<T, U> o2) {
			if (o1.getSpecificity() == null || o2.getSpecificity() == null) {
				// Shouldn't matter, as long as it is non-zero
				// Anything that will affect display should have a specificity set
				return 1;
			}
			return -o1.getSpecificity().compareTo(o2.getSpecificity());
		}
		
	}
	
}
