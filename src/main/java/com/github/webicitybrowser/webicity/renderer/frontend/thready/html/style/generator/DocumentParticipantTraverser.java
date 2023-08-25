package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.Collection;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public class DocumentParticipantTraverser implements CSSOMParticipantTraverser<DocumentStyleGenerator, DirectivePool> {

	@Override
	public DocumentStyleGenerator[] getChildren(DocumentStyleGenerator participant) {
		return participant.getChildren();
	}

	@Override
	public DocumentStyleGenerator getParent(DocumentStyleGenerator participant) {
		return participant.getParent();
	}

	@Override
	public Collection<CSSOMNode<DocumentStyleGenerator, DirectivePool>> getMatchingNodes(DocumentStyleGenerator participant) {
		return participant.getMatchingCSSOMNodes();
	}

	@Override
	public void addMatchingNode(DocumentStyleGenerator participant, CSSOMNode<DocumentStyleGenerator, DirectivePool> baseNode) {
		participant.addMatchingNode(baseNode);
	}

}
