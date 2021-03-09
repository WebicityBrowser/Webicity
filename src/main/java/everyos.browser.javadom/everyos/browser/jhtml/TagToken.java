package everyos.browser.jhtml;

public class TagToken extends Token{
	private StringBuilder name;
	private boolean selfClosing = false;
	private StringBuilder attribute_name;
	private StringBuilder attribute_value;
	private boolean isEnd;
	private String is;
	
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

	public String getIs() {
		return is;
	}
}
