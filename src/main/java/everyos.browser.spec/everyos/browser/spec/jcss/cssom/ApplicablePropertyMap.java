package everyos.browser.spec.jcss.cssom;

import java.util.HashMap;
import java.util.Map;

import everyos.browser.spec.jcss.cssom.property.InheritProperty;
import everyos.browser.spec.jcss.cssom.property.InheritPropertyImp;
import everyos.browser.spec.jcss.cssom.property.Property;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssom.property.backgroundcolor.BackgroundColorProperty;
import everyos.browser.spec.jcss.cssom.property.color.ColorProperty;
import everyos.browser.spec.jcss.cssom.property.display.DisplayProperty;
import everyos.browser.spec.jcss.cssom.property.height.HeightProperty;
import everyos.browser.spec.jcss.cssom.property.width.WidthProperty;
import everyos.browser.spec.jcss.cssvalue.color.CSSColor;
import everyos.browser.spec.jcss.cssvalue.common.Sizing;
import everyos.browser.spec.jcss.cssvalue.display.InnerDisplayType;
import everyos.browser.spec.jcss.cssvalue.display.OuterDisplayType;

public class ApplicablePropertyMap {
	
	private static final Map<PropertyName, Property> defaultProperties = new HashMap<>();
	
	private final Map<PropertyName, Property> properties;

	private ApplicablePropertyMap parent;

	public ApplicablePropertyMap(Map<PropertyName, Property> properties, ApplicablePropertyMap parent) {
		this.properties = properties;
		this.parent = parent;
	}
	
	public Property getPropertyByName(PropertyName name) {
		Property property = properties.getOrDefault(name, defaultProperties.get(name));	
		
		if (property instanceof InheritProperty) {
			return resolveInheritProperty(property);
		}
		
		return property;
	}
	
	private Property resolveInheritProperty(Property property) {
		if (parent == null) {
			property = defaultProperties.get(property.getPropertyName());
			if (property instanceof InheritProperty) {
				return ((InheritProperty) property).getDefault();
			}
			
			return property;
		} else {
			return parent.getPropertyByName(property.getPropertyName());
		}
	}

	static {
		defaultProperties.put(PropertyName.COLOR, new InheritPropertyImp(new ColorProperty(CSSColor.BLACK)));
		defaultProperties.put(PropertyName.BACKGROUND_COLOR, new BackgroundColorProperty(CSSColor.TRANSPARENT));
		defaultProperties.put(PropertyName.DISPLAY, new DisplayProperty(OuterDisplayType.INLINE, InnerDisplayType.FLOW));
		defaultProperties.put(PropertyName.WIDTH, new WidthProperty(e -> Sizing.AUTO_SIZING));
		defaultProperties.put(PropertyName.HEIGHT, new HeightProperty(e -> Sizing.AUTO_SIZING));
	}

	public static ApplicablePropertyMap empty() {
		return inherit(null);
	}
	
	public static ApplicablePropertyMap inherit(ApplicablePropertyMap parent) {
		return new ApplicablePropertyMap(Map.of(), parent);
	}
	
}
