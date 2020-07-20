package everyos.browser.webicity.renderer.html.parser;

public class CommentToken extends Token {
	public String data;

	public CommentToken(String string) {
		this.data = string;
	}
}
