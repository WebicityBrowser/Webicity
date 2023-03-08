package everyos.parser.portalhtml.tokens;

public class DoctypeToken implements Token {

	private final StringBuilder name = new StringBuilder();
	private boolean forceQuirks;

	public void setForceQuirks(boolean mode) {
		this.forceQuirks = mode;
	}
	
	public boolean getForceQuirks() {
		return this.forceQuirks;
	}

	public void appendToName(int ch) {
		name.appendCodePoint(ch);
	}

	public String getName() {
		return name.toString();
	}
	
}
