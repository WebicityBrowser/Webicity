package everyos.browser.spec.jcss.cssom.selector;

import everyos.browser.spec.jcss.cssom.selector.psuedo.PsuedoElementSelector;

public class ComplexSelector {
	
	private final ComplexSelectorPart[] parts;
	private final PsuedoElementSelector psuedoElementSelector;

	public ComplexSelector(ComplexSelectorPart[] parts, PsuedoElementSelector psuedoElementSelector) {
		this.parts = parts;
		this.psuedoElementSelector = psuedoElementSelector;
	}

	public ComplexSelectorPart[] getParts() {
		return this.parts;
	};
	
	public PsuedoElementSelector getPsuedoElementSelector() {
		return this.psuedoElementSelector;
	};
	
}
