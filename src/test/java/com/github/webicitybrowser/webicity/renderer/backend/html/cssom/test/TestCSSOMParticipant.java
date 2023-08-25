package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;

public class TestCSSOMParticipant {

	private final TestCSSOMParticipant parent;
	private final List<TestCSSOMParticipant> children;
	private final List<CSSOMNode<TestCSSOMParticipant, Object>> matchingNodes;

	public TestCSSOMParticipant(TestCSSOMParticipant parent) {
		this.parent = parent;
		this.children = new ArrayList<>();
		this.matchingNodes = new ArrayList<>();
	}
	
	public TestCSSOMParticipant getParent() {
		return this.parent;
	}

	public TestCSSOMParticipant[] getChildren() {
		return this.children.toArray(new TestCSSOMParticipant[0]);
	}

	public void addChild(TestCSSOMParticipant child) {
		children.add(child);
	}

    public Collection<CSSOMNode<TestCSSOMParticipant, Object>> getMatchingNodes() {
        return this.matchingNodes;
    }

}
