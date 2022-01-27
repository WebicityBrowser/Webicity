package everyos.browser.spec.jcss.cssom;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import everyos.browser.spec.javadom.intf.Element;
import everyos.browser.spec.javadom.intf.Node;
import everyos.browser.spec.jcss.cssom.property.Property;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssom.selector.ComplexSelectorPart;
import everyos.browser.spec.jcss.cssom.selector.simple.ClassSelector;
import everyos.browser.spec.jcss.cssom.selector.simple.IDSelector;
import everyos.browser.spec.jcss.cssom.selector.simple.SimpleSelector;
import everyos.browser.spec.jcss.cssom.selector.simple.SimpleSelectorFastRoute;
import everyos.browser.spec.jcss.cssom.selector.simple.TypeSelector;

public class CSSOMNode {
	private final Map<ComplexSelectorPart, CSSOMNode> nodes = new HashMap<>(1);
	private final Map<SimpleSelectorFastRoute, CSSOMNode> nodesFastRoute = new HashMap<>(1);
	
	private final Map<PropertyName, Property> properties = new HashMap<>(1);
	
	private ComplexSelectorPart[] selectors = new ComplexSelectorPart[0];
	
	public CSSOMNode getNode(ComplexSelectorPart selector) {
		if (selector instanceof SimpleSelectorFastRoute) {
			return nodesFastRoute.get((SimpleSelectorFastRoute) selector);
		}
		return nodes.get(selector);
	}
	
	public CSSOMNode getOrCreateNode(ComplexSelectorPart selector) {
		if (selector instanceof SimpleSelectorFastRoute) {
			return nodesFastRoute.computeIfAbsent((SimpleSelectorFastRoute) selector, (k)->new CSSOMNode());
		}
		
		if (!nodes.containsKey(selector)) {
			nodes.put(selector, new CSSOMNode());
			
			Set<ComplexSelectorPart> rawSelectors = nodes.keySet();
			selectors = rawSelectors.toArray(new ComplexSelectorPart[rawSelectors.size()]);
		}
		
		return nodes.get(selector);
	}
	
	public ComplexSelectorPart[] getSelectors(Node node) {
		int additional = 0;
		if (node instanceof Element) {
			additional = ((Element) node).getClassList().getLength();
		}
		
		ComplexSelectorPart[] allSelectors = new ComplexSelectorPart[2 + additional + this.selectors.length];
		int i = 0;
		
		if (node instanceof Element) {
			Element e = (Element) node;
			SimpleSelector next = new TypeSelector(e.getTagName());
			if (nodesFastRoute.containsKey(next)) {
				allSelectors[i++] = next;
			}
			
			String id = e.getId();
			if (id != null) {
				next = new IDSelector(id);
				if (nodesFastRoute.containsKey(next)) {
					allSelectors[i++] = next;
				}
			}
			
			for (String clsName: e.getClassList()) {
				next = new ClassSelector(clsName);
				if (nodesFastRoute.containsKey(next)) {
					allSelectors[i++] = next;
				}
			}
		}
		
		if (this.selectors.length > 0) {
			System.arraycopy(this.selectors, 0, allSelectors, i, this.selectors.length);
		}
		
		//TODO: Don't return null elements
		
		return allSelectors;
	}
	
	public void setProperty(Property property) {
		properties.put(property.getPropertyName(), property);
	}
	
	public Property getProperty(PropertyName name) {
		return properties.get(name);
	}

	public Property[] getProperties() {
		Property[] propertiesArr = new Property[properties.size()];
		
		int i = 0;
		for (PropertyName key: properties.keySet()) {
			propertiesArr[i++] = properties.get(key);
		}
		
		return propertiesArr;
	}
	
}
