package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.ChildFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.test.TestCSSOMParticipant;

public class CSSOMTest {

	@Test
	@DisplayName("Root CSSOMNode matches root participant")
	public void rootCSSOMNodeMatchesRootParticipant() {
		CSSOMNode<Object, Object> rootNode = CSSOMNode.create();
		CSSOMTree<Object, Object> cssomTree = CSSOMTree.create(rootNode);
		Object node = new Object();
		CSSOMParticipant<Object> rootParticipant = new TestCSSOMParticipant<>(node, null);
		CSSOMResult<Object, Object> result = cssomTree.apply(rootParticipant);
		List<CSSOMNode<Object, Object>> matchingNodes = result.getMatchingNodes(node);
		Assertions.assertEquals(1, matchingNodes.size());
		Assertions.assertEquals(rootNode, matchingNodes.get(0));
	}
	
	@Test
	@DisplayName("Child CSSOMNode matches child filter")
	public void childCSSOMNodeMatchesGlobFilter() {
		CSSOMNode<Object, Object> rootNode = CSSOMNode.create();
		CSSOMNode<Object, Object> childNode = rootNode.createChild(new ChildFilter<>(), 0);
		CSSOMTree<Object, Object> cssomTree = CSSOMTree.create(rootNode);
		Object node1 = new Object();
		TestCSSOMParticipant<Object> rootParticipant = new TestCSSOMParticipant<>(node1, null);
		Object node2 = new Object();
		CSSOMParticipant<Object> childParticipant = new TestCSSOMParticipant<>(node2, rootParticipant);
		rootParticipant.addChild(childParticipant);
		CSSOMResult<Object, Object> result = cssomTree.apply(rootParticipant);
		List<CSSOMNode<Object, Object>> rootMatchingNodes = result.getMatchingNodes(node1);
		Assertions.assertEquals(1, rootMatchingNodes.size());
		Assertions.assertEquals(rootNode, rootMatchingNodes.get(0));
		List<CSSOMNode<Object, Object>> childMatchingNodes = result.getMatchingNodes(node2);
		Assertions.assertEquals(1, childMatchingNodes.size());
		Assertions.assertEquals(childNode, childMatchingNodes.get(0));
	}
	
}
