package everyos.browser.spec.jcss.cssom.property.display;

import everyos.browser.spec.jcss.cssom.property.Property;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssvalue.display.InnerDisplayType;
import everyos.browser.spec.jcss.cssvalue.display.OuterDisplayType;

public class DisplayProperty implements Property {
	
	private final OuterDisplayType outerDisplayType;
	private final InnerDisplayType innerDisplayType;

	public DisplayProperty(OuterDisplayType outerDisplayType, InnerDisplayType innerDisplayType) {
		this.outerDisplayType = outerDisplayType;
		this.innerDisplayType = innerDisplayType;
	}
	
	public OuterDisplayType getOuterDisplayType() {
		return this.outerDisplayType;
	}
	
	public InnerDisplayType getInnerDisplayType() {
		return this.innerDisplayType;
	}

	@Override
	public PropertyName getPropertyName() {
		return PropertyName.DISPLAY;
	}
	
}
