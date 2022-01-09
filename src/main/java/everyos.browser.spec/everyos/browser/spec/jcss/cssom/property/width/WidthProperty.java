package everyos.browser.spec.jcss.cssom.property.width;

import everyos.browser.spec.jcss.cssom.property.Property;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssvalue.common.Sizing;

public class WidthProperty implements Property {

	private final Sizing sizing;

	public WidthProperty(Sizing sizing) {
		this.sizing = sizing;
	}

	@Override
	public PropertyName getPropertyName() {
		return PropertyName.WIDTH;
	}

	public Sizing getComputedWidthSizing() {
		return this.sizing;
	}
	
}
