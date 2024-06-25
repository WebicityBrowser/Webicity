package com.github.webicitybrowser.spiderhtml.token;

public class CharacterToken implements Token {

	private int character;

	public CharacterToken(int ch) {
		this.character = ch;
	}

	public int getCharacter() {
		return this.character;
	}

}
