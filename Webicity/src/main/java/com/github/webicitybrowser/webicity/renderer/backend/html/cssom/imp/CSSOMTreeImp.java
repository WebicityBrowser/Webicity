package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.BitSet;

import com.github.webicitybrowser.spec.css.selectors.combinator.ChildCombinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.Combinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.DescendantCombinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.NextSiblingCombinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.SubsequentSiblingCombinator;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTraverseContext;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;

public class CSSOMTreeImp<T, U> implements CSSOMTree<T, U> {

	private final CSSOMNode<T, U> rootNode;
	private final SelectorMatchUtil<T, U> selectorMatchUtil = new SelectorMatchUtil<>();

	public CSSOMTreeImp(CSSOMNode<T, U> rootNode) {
		this.rootNode = rootNode;
	}

	@Override
	public void apply(CSSOMTraverseContext<T, U> context) {
		matchSelectorParts(context.allParticipantsBitSet(), rootNode, context);
	}

	private void matchSelectorParts(BitSet current, CSSOMNode<T, U> node, CSSOMTraverseContext<T, U> context) {
		BitSet newMatched = selectorMatchUtil.matchIndividualSelectorPart(current, node, context);
		applyMatch(node, newMatched, context);
		for (CSSOMNode<T, U> child: node.getChildren()) {
			if (child.getSelectorPart() instanceof Combinator) {
				matchCombinatedSelectorPart(newMatched, child, context);
			} else {
				matchSelectorParts(newMatched, child, context);
			}
		}
	}

	private void matchCombinatedSelectorPart(BitSet current, CSSOMNode<T, U> node, CSSOMTraverseContext<T, U> context) {
		Combinator combinator = (Combinator) node.getSelectorPart();
		if (combinator instanceof DescendantCombinator) {
			BitSet matchedDescendants = CombinatorMatchUtil.getDescendants(current, context);
			matchSelectorParts(matchedDescendants, node, context);
		} else if (combinator instanceof ChildCombinator) {
			BitSet matchedChildren = CombinatorMatchUtil.getChildren(current, context);
			matchSelectorParts(matchedChildren, node, context);
		} else if (combinator instanceof SubsequentSiblingCombinator) {
			BitSet matchedSubsequentSiblings = CombinatorMatchUtil.getSubsequentSiblings(current, context);
			matchSelectorParts(matchedSubsequentSiblings, node, context);
		} else if (combinator instanceof NextSiblingCombinator) {
			BitSet matchedNextSiblings = CombinatorMatchUtil.getNextSiblings(current, context);
			matchSelectorParts(matchedNextSiblings, node, context);
		} else {
			throw new IllegalArgumentException("Unknown combinator: " + combinator);
		}
	}

	private void applyMatch(CSSOMNode<T, U> node, BitSet matched, CSSOMTraverseContext<T, U> context) {
		CSSOMParticipantTraverser<T, U> traverser = context.traverser();

		if (!node.isPopulated()) return;
		for (int matchedIndex = matched.nextSetBit(0); matchedIndex >= 0; matchedIndex = matched.nextSetBit(matchedIndex + 1)) {
			traverser.addMatchingNode(context.allParticipants().get(matchedIndex), node);
		}
	}
	
}
