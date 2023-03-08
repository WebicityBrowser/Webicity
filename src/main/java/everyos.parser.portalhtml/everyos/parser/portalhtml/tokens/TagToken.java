package everyos.parser.portalhtml.tokens;

public class TagToken implements Token {
	
	private final StringBuilder name;

	public TagToken() {
		this.name = new StringBuilder();
	}
	
	public TagToken(String name) {
		this.name = new StringBuilder(name);
	}

	public void appendToName(int c) {
		name.appendCodePoint(c);
	}
	
	public String getName() {
		return name.toString();
	}

}
