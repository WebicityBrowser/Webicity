package everyos.browser.jhtml.parser;

public class CharToken extends Token {
	private char ch;

	public CharToken(char ch) {
		this.ch = ch;
	}
	public CharToken(int ch) {
		this.ch = (char) ch;
	}
	
	public char getCharacter() {
		return ch;
	}
}