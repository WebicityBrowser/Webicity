package everyos.parser.portalhtml.tokens;

import java.util.ArrayList;
import java.util.List;

public class StartTagToken extends TagToken {

	private final List<TokenAttribute> attributes = new ArrayList<>();
	
	private TokenAttribute currentAttribute;
	private boolean selfClosing = false;
	
	public StartTagToken() {}

	public StartTagToken(String name) {
		super(name);
	}

	public void startNewAttribute() {
		currentAttribute = new TokenAttribute(new StringBuilder(), new StringBuilder());
		attributes.add(currentAttribute);
	}

	public void appendToAttributeName(int ch) {
		currentAttribute.name().appendCodePoint(ch);
	}

	public void appendToAttributeValue(int ch) {
		currentAttribute.value().appendCodePoint(ch);
	}
	
	public String getAttributeValue(String name) {
		for (TokenAttribute attribute: attributes) {
			if (attribute.name().equals(name)) {
				return attribute.value().toString();
			}
		}
		
		return null;
	}

	public TokenAttribute[] getAttributes() {
		return attributes.toArray(new TokenAttribute[0]);
	}

	public void setSelfClosingFlag() {
		this.selfClosing = true;
	}
	
	public boolean getSelfClosingFlag() {
		return this.selfClosing;
	}

	public void acknowledgeSelfClosingFlag() {
		// TODO Auto-generated method stub
		
	}
	
	public static record TokenAttribute(StringBuilder name, StringBuilder value) {}

}
