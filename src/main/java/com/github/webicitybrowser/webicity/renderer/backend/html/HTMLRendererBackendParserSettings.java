package com.github.webicitybrowser.webicity.renderer.backend.html;


import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.html.parse.ParserSettings;
import com.github.webicitybrowser.spec.html.parse.CharacterReferenceLookup;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.webicity.renderer.backend.html.tags.TagAction;


public class HTMLRendererBackendParserSettings implements ParserSettings {
	
	private final CharacterReferenceLookup unicodeLookup;
	private final TagActions tagActions;

	public HTMLRendererBackendParserSettings(CharacterReferenceLookup unicodeLookup, TagActions actions) {
		this.unicodeLookup = unicodeLookup;
		this.tagActions = actions;
	}

	@Override
	public CharacterReferenceLookup getUnicodeLookup() {
		return this.unicodeLookup;
	}

	@Override
	public void onNodePopped(Node node) {
		if(
			node instanceof Element element &&
			element.getNamespace().equals(Namespace.HTML_NAMESPACE)
		) {
			TagAction tagAction = tagActions.getAction(element.getLocalName());
			tagAction.handleTag(element);
		}
	}

}
