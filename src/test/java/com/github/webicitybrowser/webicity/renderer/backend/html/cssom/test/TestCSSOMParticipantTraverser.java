package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.test;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class TestCSSOMParticipantTraverser implements CSSOMParticipantTraverser<TestCSSOMParticipant> {

	@Override
	public TestCSSOMParticipant[] getChildren(TestCSSOMParticipant item) {
		return item.getChildren();
	}

}
