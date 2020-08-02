package everyos.browser.webicity.renderer.html.parser;

public class CharToken extends Token {
	public char ch;

	public CharToken(char ch) {
		this.ch = ch;
	}
	public CharToken(int ch) {
		this.ch = (char) ch;
	}
}
