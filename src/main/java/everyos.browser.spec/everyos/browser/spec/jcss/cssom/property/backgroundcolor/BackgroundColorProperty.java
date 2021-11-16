package everyos.browser.spec.jcss.cssom.property.backgroundcolor;

import everyos.browser.spec.jcss.cssom.property.Property;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.engine.ribbon.core.graphics.paintfill.Color;

public class BackgroundColorProperty implements Property {
	private Color color;

	public BackgroundColorProperty(Color color) {
		this.color = color;
	}

	public Color getComputedColor() {
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
