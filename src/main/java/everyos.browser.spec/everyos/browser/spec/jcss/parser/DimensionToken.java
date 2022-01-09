package everyos.browser.spec.jcss.parser;

public class DimensionToken implements CSSToken {
	
	private final Number value;
	private final String type;
	private final String unit;

	public DimensionToken(Number value, String type, String unit) {
		this.value = value;
		this.type = type;
		this.unit = unit;
	}
	
	public Number getValue() {
		return this.value;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getUnit() {
		return this.unit;
	}
	
}
