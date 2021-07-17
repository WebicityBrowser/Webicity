package everyos.browser.jhtml.parser;

public class DoctypeToken extends Token {
	private boolean forceQuirks;
	private StringBuilder nameBuilder = new StringBuilder();
	private String publicIdentifier;
	
	public StringBuilder getNameBuilder() {
		return nameBuilder;
	}
	
	public void setForceQuirks(boolean b) {
		forceQuirks = b;
	}
	public boolean getForceQuirks() {
		return forceQuirks;
	}
	
	public void setPublicIdentifier(String string) {
		this.publicIdentifier = string;
	}
	
	public String getPublicIdentifier() {
		return this.publicIdentifier;
	}

	public void setSystemIdentifier(String string) {
		
	}
}