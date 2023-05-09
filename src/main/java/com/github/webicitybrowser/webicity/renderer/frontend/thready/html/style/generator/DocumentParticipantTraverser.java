package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class DocumentParticipantTraverser implements CSSOMParticipantTraverser<Node> {

	@Override
	public Node[] getChildren(Node node) {
		return node.getChildNodes().toArray();
	}

}
