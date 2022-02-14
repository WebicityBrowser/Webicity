package everyos.browser.spec.jcss.cssom.property.color;

import everyos.browser.spec.jcss.cssom.property.Property;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssvalue.color.CSSColor;

public class ColorProperty implements Property {
	
	private CSSColor color;

	public ColorProperty(CSSColor color) {
		this.color = color;
	}

	public CSSColor getComputedColor() {
		return this.color;
	}

	@Override
	public PropertyName getPropertyName() {
		return PropertyName.COLOR;
	}
	
}
