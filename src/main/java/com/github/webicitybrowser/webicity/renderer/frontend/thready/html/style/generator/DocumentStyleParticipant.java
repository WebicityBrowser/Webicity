package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.support.NodeList;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipant;

public class DocumentStyleParticipant implements CSSOMParticipant<Node> {

	private final Node value;

	public DocumentStyleParticipant(Node value) {
		this.value = value;
	}
	
	@Override
	public Node getValue() {
		return value;
	}

	@Override
	public CSSOMParticipant<Node> getParent() {
		return
			value.getParentNode() == null ?
			null :
			new DocumentStyleParticipant(value.getParentNode());
	}

	@Override
	public List<CSSOMParticipant<Node>> getChildren() {
		NodeList childNodes = value.getChildNodes();
		List<CSSOMParticipant<Node>> children = new ArrayList<>(childNodes.getLength());
		for (Node childNode: childNodes) {
			children.add(new DocumentStyleParticipant(childNode));
		}
		
		return children;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof DocumentStyleParticipant participant &&
		participant.getValue().equals(value);
	}

}
