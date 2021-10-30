package everyos.browser.spec.jcss.cssom;

import java.util.HashMap;

import everyos.browser.spec.jcss.cssom.property.Property;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssom.property.backgroundcolor.BackgroundColorProperty;
import everyos.browser.spec.jcss.cssom.property.color.ColorProperty;
import everyos.browser.spec.jcss.cssom.property.display.DisplayProperty;
import everyos.browser.spec.jcss.cssvalue.display.InnerDisplayType;
import everyos.browser.spec.jcss.cssvalue.display.OuterDisplayType;
import everyos.engine.ribbon.core.graphics.Color;

public class ApplicablePropertyMap {
	
	private static final HashMap<PropertyName, Property> defaultProperties = new HashMap<>();
	
	private final HashMap<PropertyName, Property> properties;

	public ApplicablePropertyMap(HashMap<PropertyName, Property> properties) {
		this.properties = properties;
	}
	
	public Property getPropertyByName(PropertyName name) {
		return properties.getOrDefault(name, defaultProperties.get(name));	
	}
	
	static {
		defaultProperties.put(PropertyName.COLOR, new ColorProperty(Color.BLACK));
		defaultProperties.put(PropertyName.BACKGROUND_COLOR, new BackgroundColorProperty(new Color(0, 0, 0, 0)));
		defaultProperties.put(PropertyName.DISPLAY, new DisplayProperty(OuterDisplayType.INLINE, InnerDisplayType.FLOW));
	}
	
}
