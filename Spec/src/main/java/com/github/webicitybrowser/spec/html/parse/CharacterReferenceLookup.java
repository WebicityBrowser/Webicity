package com.github.webicitybrowser.spec.html.parse;

public interface CharacterReferenceLookup {

	String[] getEntityNames();

	int[] getCodePointsForNamedEntity(String reference);

}
