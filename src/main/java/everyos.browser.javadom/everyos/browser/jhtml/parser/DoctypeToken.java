package everyos.browser.jhtml.parser;

public class DoctypeToken extends Token {
	private boolean forceQuirks;
	private StringBuilder nameBuilder = new StringBuilder();
	
	public StringBuilder getNameBuilder() {
		return nameBuilder;
	}
	public void setForceQuirks(boolean b) {
		forceQuirks = b;
	}
	public boolean getForceQuirks() {
		return forceQuirks;
	}
}