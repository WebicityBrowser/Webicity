package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.BitSet;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTraverseContext;

public final class CombinatorMatchUtil {

	private CombinatorMatchUtil() {}

	public static <T, U> BitSet getDescendants(BitSet current, CSSOMTraverseContext<T, U> context) {
		BitSet descendants = new BitSet();
		for (int i = 0; i < current.size(); i++) {
			if (!current.get(i)) continue;
			int queryStart = i + 1;
			int queryEnd = context.allParticipantsEnds().get(i);
			if (queryEnd < queryStart) continue;
			for (int j = queryStart; j <= queryEnd; j++) {
				descendants.set(j);
			}
			i = queryEnd + 1;
		}

		return descendants;
	}

	public static <T, U> BitSet getChildren(BitSet current, CSSOMTraverseContext<T, U> context) {
		BitSet children = new BitSet();
		for (int i = 0; i < current.size(); i++) {
			if (!current.get(i)) continue;
			T currentNode = context.allParticipants().get(i);
			for (T child: context.traverser().getChildren(currentNode)) {
				int participantId = context.participantIds().getOrDefault(child, -1);
				// TODO: There is a race condition elsewhere I still need to effect, it is throwing off styling
				if (participantId == -1) continue;
				children.set(participantId);
			}
		}


		return children;
	}

	public static <T, U> BitSet getSubsequentSiblings(BitSet current, CSSOMTraverseContext<T, U> context) {
		BitSet siblings = new BitSet();
		for (int i = 0; i < current.size(); i++) {
			if (!current.get(i)) continue;
			T currentNode = context.allParticipants().get(i);
			T parent = context.traverser().getParent(currentNode);
			if (parent == null) continue;
			boolean foundCurrent = false;
			for (T sibling: context.traverser().getChildren(parent)) {
				foundCurrent |= sibling == currentNode;
				if (!foundCurrent || sibling == currentNode) continue;
				int participantId = context.participantIds().getOrDefault(sibling, -1);
				if (participantId == -1) continue;
				siblings.set(participantId);
			}
		}

		return siblings;
	}

	// Use indexOf
	public static <T, U> BitSet getNextSiblings(BitSet current, CSSOMTraverseContext<T, U> context) {
		BitSet siblings = new BitSet();
		for (int i = 0; i < current.size(); i++) {
			if (!current.get(i)) continue;
			T currentNode = context.allParticipants().get(i);
			T parent = context.traverser().getParent(currentNode);
			if (parent == null) continue;
			boolean foundCurrent = false;
			for (T sibling: context.traverser().getChildren(parent)) {
				foundCurrent |= sibling == currentNode;
				if (!foundCurrent || sibling == currentNode) continue;
				int participantId = context.participantIds().getOrDefault(sibling, -1);
				if (participantId == -1) continue;
				siblings.set(participantId);
				break;
			}
		}

		return siblings;
	}

}
