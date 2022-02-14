package everyos.browser.spec.jcss.cssom.property.backgroundcolor;

import everyos.browser.spec.jcss.cssom.property.Property;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssvalue.color.CSSColor;

public class BackgroundColorProperty implements Property {
	
	private CSSColor color;

	public BackgroundColorProperty(CSSColor color) {
		this.color = color;
	}

	public CSSColor getComputedColor() {
		return this.color;
	}

	@Override
	public PropertyName getPropertyName() {
		return PropertyName.BACKGROUND_COLOR;
	}
	
	@Override
	public String toString() {
		return "BackgroundColorProperty [color=" + color.toString() + "]";
	}
	
}
