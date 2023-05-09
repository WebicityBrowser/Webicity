package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.test;

import java.util.ArrayList;
import java.util.List;

public class TestCSSOMParticipant {

	private final TestCSSOMParticipant parent;
	private final List<TestCSSOMParticipant> children;

	public TestCSSOMParticipant(TestCSSOMParticipant parent) {
		this.parent = parent;
		this.children = new ArrayList<>();
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

}
