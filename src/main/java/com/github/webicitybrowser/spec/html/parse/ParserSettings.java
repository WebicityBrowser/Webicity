package com.github.webicitybrowser.spec.html.parse;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.tags.TagAction;

public interface ParserSettings {

	void onNodePopped(Node node);
	
	CharacterReferenceLookup getUnicodeLookup();
	
}
