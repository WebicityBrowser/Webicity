package everyos.parser.portalhtml.tokens;

//TODO: Cache tokens?
public class CharacterToken implements Token {
	
	private final int character;

	public CharacterToken(int ch) {
		this.character = ch;
	}
	
	public int getCharacter() {
		return this.character;
	}

}
