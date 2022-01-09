package everyos.browser.spec.jcss.cssom.property.fontsize;

import everyos.browser.spec.jcss.cssom.property.Property;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssvalue.common.Sizing;

public class FontSizeProperty implements Property {
	
	private final Sizing sizing;

	public FontSizeProperty(Sizing sizing) {
		this.sizing = sizing;
	}

	@Override
	public PropertyName getPropertyName() {
		return PropertyName.FONT_SIZE;
	}
	
	public Sizing getFontSize() {
		return sizing;
	}

}
