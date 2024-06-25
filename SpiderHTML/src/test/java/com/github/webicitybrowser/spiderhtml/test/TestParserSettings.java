package com.github.webicitybrowser.spiderhtml.test;

import org.mockito.Mockito;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.html.parse.CharacterReferenceLookup;
import com.github.webicitybrowser.spec.html.parse.ParserSettings;

public class TestParserSettings implements ParserSettings {

	@Override
	public void onNodePopped(Node node) {
		
	}

	@Override
	public CharacterReferenceLookup getUnicodeLookup() {
		CharacterReferenceLookup lookup = Mockito.mock(CharacterReferenceLookup.class);
		Mockito.when(lookup.getCodePointsForNamedEntity("&lt;")).thenReturn(new int[] { '<' });
		Mockito.when(lookup.getEntityNames()).thenReturn(new String[] { "&lt;" });
		
		return lookup;
	}

}
