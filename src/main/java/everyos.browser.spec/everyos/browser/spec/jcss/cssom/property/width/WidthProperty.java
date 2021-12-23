package everyos.browser.spec.jcss.cssom.property.width;

import everyos.browser.spec.jcss.cssom.property.Property;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssvalue.sizing.Sizing;

public class WidthProperty implements Property {

	public WidthProperty(Sizing sizing) {
		
	}

	@Override
	public PropertyName getPropertyName() {
		return PropertyName.WIDTH;
	}
	
}
