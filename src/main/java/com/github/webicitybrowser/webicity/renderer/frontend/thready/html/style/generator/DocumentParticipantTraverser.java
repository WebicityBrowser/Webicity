package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.Collection;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMRuleMap;

public class DocumentParticipantTraverser implements CSSOMParticipantTraverser<DocumentStyleGenerator, CSSOMRuleMap> {

	@Override
	public DocumentStyleGenerator[] getChildren(DocumentStyleGenerator participant) {
		return participant.getChildren();
	}

	@Override
	public DocumentStyleGenerator getParent(DocumentStyleGenerator participant) {
		return participant.getParent();
	}

	@Override
	public Collection<CSSOMNode<DocumentStyleGenerator, CSSOMRuleMap>> getMatchingNodes(DocumentStyleGenerator participant) {
		return participant.getMatchingCSSOMNodes();
	}

	@Override
	public void addMatchingNode(DocumentStyleGenerator participant, CSSOMNode<DocumentStyleGenerator, CSSOMRuleMap> baseNode) {
		participant.addMatchingNode(baseNode);
	}

}
