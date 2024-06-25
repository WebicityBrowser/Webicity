package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.test;

import java.util.Collection;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class TestCSSOMParticipantTraverser implements CSSOMParticipantTraverser<TestCSSOMParticipant, Object> {

	@Override
	public TestCSSOMParticipant[] getChildren(TestCSSOMParticipant participant) {
		return participant.getChildren();
	}

	@Override
	public TestCSSOMParticipant getParent(TestCSSOMParticipant participant) {
		return participant.getParent();
	}

	@Override
	public Collection<CSSOMNode<TestCSSOMParticipant, Object>> getMatchingNodes(TestCSSOMParticipant participant) {
		return participant.getMatchingNodes();
	}

	@Override
	public void addMatchingNode(TestCSSOMParticipant participant, CSSOMNode<TestCSSOMParticipant, Object> baseNode) {
		participant.getMatchingNodes().add(baseNode);
	}

}
