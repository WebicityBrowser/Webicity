package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.ChildFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.test.TestCSSOMParticipant;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.test.TestCSSOMParticipantTraverser;

public class CSSOMTest {

	@Test
	@DisplayName("Root CSSOMNode matches root participant")
	public void rootCSSOMNodeMatchesRootParticipant() {
		CSSOMNode<TestCSSOMParticipant, Object> rootNode = CSSOMNode.create(null, null);
		CSSOMTree<TestCSSOMParticipant, Object> cssomTree = CSSOMTree.create(rootNode);
		TestCSSOMParticipant rootParticipant = new TestCSSOMParticipant(null);
		CSSOMResult<TestCSSOMParticipant, Object> result = cssomTree.apply(rootParticipant, new TestCSSOMParticipantTraverser());
		List<CSSOMNode<TestCSSOMParticipant, Object>> matchingNodes = List.copyOf(result.getMatchingNodes(rootParticipant));
		Assertions.assertEquals(1, matchingNodes.size());
		Assertions.assertEquals(rootNode, matchingNodes.get(0));
	}
	
	@Test
	@DisplayName("Child CSSOMNode matches child filter")
	public void childCSSOMNodeMatchesChildFilter() {
		CSSOMNode<TestCSSOMParticipant, Object> rootNode = CSSOMNode.create(null, null);
		CSSOMNode<TestCSSOMParticipant, Object> combinatorNode = rootNode.createChild(new ChildFilter<>(), 0);
		CSSOMNode<TestCSSOMParticipant, Object> childNode = combinatorNode.createChild((_1, el, _2) -> List.of(el), 0);
		childNode.addNodeProperties(new Object()); // Due to optimizations, the node must be populated
		CSSOMTree<TestCSSOMParticipant, Object> cssomTree = CSSOMTree.create(rootNode);
		TestCSSOMParticipant rootParticipant = new TestCSSOMParticipant(null);
		TestCSSOMParticipant childParticipant = new TestCSSOMParticipant(rootParticipant);
		rootParticipant.addChild(childParticipant);
		CSSOMResult<TestCSSOMParticipant, Object> result = cssomTree.apply(rootParticipant, new TestCSSOMParticipantTraverser());
		List<CSSOMNode<TestCSSOMParticipant, Object>> rootMatchingNodes = List.copyOf(result.getMatchingNodes(rootParticipant));
		Assertions.assertEquals(1, rootMatchingNodes.size());
		Assertions.assertEquals(rootNode, rootMatchingNodes.get(0));
		List<CSSOMNode<TestCSSOMParticipant, Object>> childMatchingNodes = List.copyOf(result.getMatchingNodes(childParticipant));
		Assertions.assertEquals(3, childMatchingNodes.size()); // Root node, combinator node, child node
		Assertions.assertTrue(childMatchingNodes.contains(childNode));
	}
	
}
