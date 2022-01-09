package everyos.browser.spec.jcss.cssom.property.height;

import everyos.browser.spec.jcss.cssom.property.Property;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssvalue.common.Sizing;

public class HeightProperty implements Property {

	private final Sizing sizing;

	public HeightProperty(Sizing sizing) {
		this.sizing = sizing;
	}

	@Override
	public PropertyName getPropertyName() {
		return PropertyName.HEIGHT;
	}

	public Sizing getComputedHeightSizing() {
		return this.sizing;
	}
	
}
