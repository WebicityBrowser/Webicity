package everyos.browser.spec.jhtml.parser;

import java.util.HashMap;

public class TagToken extends Token{
	private StringBuilder name;
	@SuppressWarnings("unused")
	private boolean selfClosing = false;
	private StringBuilder attribute_name;
	private StringBuilder attribute_value;
	private boolean isEnd;
	private HashMap<String, String> attributes = new HashMap<>();
	
	public TagToken(String name, boolean isEnd) {
		this.name = new StringBuilder(8);
		this.name.append(name);
		this.isEnd = isEnd;
	}

	public StringBuilder getNameBuilder() {
		return name;
	}
	
	public StringBuilder getAttributeNameBuilder() {
		return attribute_name;
	}
	
	public StringBuilder getAttributeValueBuilder() {
		return attribute_value;
	}

	public void resetAttributeNameBuilder() {
		if (attribute_name != null && attribute_value != null) {
			attributes.put(attribute_name.toString().toLowerCase(), attribute_value.toString());
		}
		attribute_name = new StringBuilder(8);
	}
	public void resetAttributeValueBuilder() {
		attribute_value = new StringBuilder(8);
	}

	public void setSelfClosing(boolean b) {
		selfClosing = b;
	}

	public boolean getIsEnd() {
		return isEnd;
	}
	
	public HashMap<String, String> getAttributes() {
		resetAttributeNameBuilder();
		return attributes;
	}
}
