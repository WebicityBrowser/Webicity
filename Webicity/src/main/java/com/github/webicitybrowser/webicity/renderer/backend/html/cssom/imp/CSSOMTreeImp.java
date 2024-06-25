package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.List;
import java.util.Set;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;

public class CSSOMTreeImp<T, U> implements CSSOMTree<T, U> {

	private final CSSOMNode<T, U> rootCSSOMNode;

	public CSSOMTreeImp(CSSOMNode<T, U> rootNode) {
		this.rootCSSOMNode = rootNode;
	}

	@Override
	public void apply(T participant, CSSOMParticipantTraverser<T, U> traverser) {
		CSSOMTips<T, U> tips = createCSSOMTips();

		TraverseContext<T, U> context = new TraverseContext<>(traverser, tips);
		traverseDOM(participant, context);
	}

	private CSSOMTips<T, U> createCSSOMTips() {
		CSSOMTips<T, U> tips = new CSSOMTips<>();

		addTips(tips, rootCSSOMNode);

		return tips;
	}

	private void traverseDOM(T participant, TraverseContext<T, U> context) {
		CSSOMParticipantTraverser<T, U> traverser = context.traverser();
		traverser.addMatchingNode(participant, rootCSSOMNode);

		Set<CSSOMNode<T, U>> possibleCSSOMNodes = context.tips().getPossibleCSSOMNodes(participant, context);
		for (CSSOMNode<T, U> possibleCSSOMNode : possibleCSSOMNodes) {
			if (traceToMatchingNode(participant, possibleCSSOMNode.getParent(), context)) {
				traverser.addMatchingNode(participant, possibleCSSOMNode);
			};
		}
		
		for (T child: context.traverser().getChildren(participant)) {
			traverseDOM(child, context);
		}
	}

	private boolean traceToMatchingNode(T participant, CSSOMNode<T, U> baseNode, TraverseContext<T, U> context) {
		CSSOMParticipantTraverser<T, U> traverser = context.traverser();

		if (baseNode.getParent() == null) {
			return true;
		}

		if (traverser.getMatchingNodes(participant).contains(baseNode)) {
			return true;
		}

		CSSOMFilter<T, U> filter = baseNode.getFilter();
		List<T> matches = filter.filter(
			traverser.getMatchingNodes(participant),
			participant,
			context.traverser()
		);

		for (T match : matches) {
			if (traceToMatchingNode(match, baseNode.getParent(), context)) {
				traverser.addMatchingNode(participant, baseNode);
				return true;
			}
		}

		return false;
	}

	private void addTips(CSSOMTips<T, U> tips, CSSOMNode<T, U> base) {
		// NOTE: The very root node (which won't be traversed by the recursion)
		// is checked separately
		// This is why the addTip is in the loop instead of outside
		for (CSSOMNode<T, U> child : base.getChildren()) {
			if (child.isPopulated()) {
				tips.addTip(child);
			}
			addTips(tips, child);
		}
	}
	
}
