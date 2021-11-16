package everyos.browser.spec.jcss.cssom.property.color;

import everyos.browser.spec.jcss.cssom.property.Property;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.engine.ribbon.core.graphics.paintfill.Color;

public class ColorProperty implements Property {
	private Color color;

	public ColorProperty(Color color) {
		this.color = color;
	}

	public Color getComputedColor() {
		return this.color;
	}

	@Override
	public PropertyName getPropertyName() {
		return PropertyName.COLOR;
	}
}
