package everyos.browser.spec.jcss.cssom;

import java.util.HashMap;
import java.util.Map;

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
	
	private static final int BASE_NUMBER_OF_FAST_PATHS = 2;
	
	private final Map<ComplexSelectorPart, CSSOMNode> nodes = new HashMap<>(1);
	private final Map<SimpleSelectorFastRoute, CSSOMNode> nodesFastRoute = new HashMap<>(1);
	
	private final Map<PropertyName, Property> properties = new HashMap<>(1);
	
	private ComplexSelectorPart[] selectors = new ComplexSelectorPart[0];
	
	//
	
	public CSSOMNode getNode(ComplexSelectorPart selector) {
		if (selector instanceof SimpleSelectorFastRoute) {
			return nodesFastRoute.get((SimpleSelectorFastRoute) selector);
		}
		
		return nodes.get(selector);
	}
	
	public CSSOMNode getOrCreateNode(ComplexSelectorPart selector) {
		if (selector instanceof SimpleSelectorFastRoute) {
			return nodesFastRoute.computeIfAbsent((SimpleSelectorFastRoute) selector, (k) -> new CSSOMNode());
		}
		
		createNodeIfNotPresent(selector);
		return nodes.get(selector);
	}

	public ComplexSelectorPart[] getSelectors(Node node) {
		int numberOfPossibleSelectors = getNumberOfSelectorsToTryMatching(node);
		ComplexSelectorPart[] selectors = new ComplexSelectorPart[numberOfPossibleSelectors];
		
		int pos = addNormalSelectors(selectors, 0);
		
		if (node instanceof Element) {
			pos = addElementFastRouteSelectors(selectors, (Element) node, pos);
		}
		
		ComplexSelectorPart[] selectorsCopy = new ComplexSelectorPart[pos];
		System.arraycopy(selectors, 0, selectorsCopy, 0, pos);
		
		return selectorsCopy;
	}

	public void setProperty(Property property) {
		properties.put(property.getPropertyName(), property);
	}
	
	public Property getProperty(PropertyName name) {
		return properties.get(name);
	}

	public Property[] getProperties() {
		return properties.values().toArray(new Property[0]);
	}
	
	//
	
	private void createNodeIfNotPresent(ComplexSelectorPart selector) {
		if (!nodes.containsKey(selector)) {
			nodes.put(selector, new CSSOMNode());
			updateSelectorCache();
		}
	}

	private void updateSelectorCache() {
		selectors = nodes.keySet().toArray(new ComplexSelectorPart[nodes.size()]);
	}
	
	private int getNumberOfSelectorsToTryMatching(Node relatedNode) {
		int numberOfExtraFastPaths = 0;
		if (relatedNode instanceof Element) {
			numberOfExtraFastPaths = ((Element) relatedNode).getClassList().getLength();
		}
		
		return BASE_NUMBER_OF_FAST_PATHS + numberOfExtraFastPaths + this.selectors.length;
	}
	
	private int addNormalSelectors(ComplexSelectorPart[] selectors, int pos) {
		if (this.selectors.length > 0) {
			System.arraycopy(this.selectors, 0, selectors, pos, this.selectors.length);
		}
		
		return pos + this.selectors.length;
	}
	
	private int addElementFastRouteSelectors(ComplexSelectorPart[] selectors, Element element, int pos) {
		int newPos = pos;
		
		if (addTypeSelector(selectors, element, newPos)) {
			newPos++;
		}
		if (addIDSelector(selectors, element, newPos)) {
			newPos++;
		}
		newPos += addClassSelectors(selectors, element, newPos);
		
		return newPos;
	}

	private boolean addTypeSelector(ComplexSelectorPart[] selectors, Element element, int pos) {
		SimpleSelector typeSelector = new TypeSelector(element.getTagName());
		if (nodesFastRoute.containsKey(typeSelector)) {
			selectors[pos] = typeSelector;
			return true;
		}
		
		return false;
	}
	
	private boolean addIDSelector(ComplexSelectorPart[] selectors, Element element, int pos) {
		String id = element.getId();
		if (id == null) {
			return false;
		}
		
		SimpleSelector idSelector = new IDSelector(id);
		if (nodesFastRoute.containsKey(idSelector)) {
			selectors[pos] = idSelector;
			return true;
		}
		
		return false;
	}
	
	private int addClassSelectors(ComplexSelectorPart[] selectors, Element element, int pos) {
		int i = 0;
		for (String className: element.getClassList()) {
			SimpleSelector classSelector = new ClassSelector(className);
			if (nodesFastRoute.containsKey(classSelector)) {
				selectors[pos + i] = classSelector;
				i++;
			}
		}
		
		return i;
	}
	
}
