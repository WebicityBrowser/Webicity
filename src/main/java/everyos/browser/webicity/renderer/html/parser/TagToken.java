package everyos.browser.webicity.renderer.html.parser;

public class TagToken extends Token{
	public StringBuilder name;
	public boolean selfClosing = false;
	public StringBuilder attribute_name;
	public StringBuilder attribute_value;
	public boolean isEnd;
	public String is;
	
	public TagToken(String name, boolean isEnd) {
		this.name = new StringBuilder(8);
		this.name.append(name);
		this.isEnd = isEnd;
	}
}
