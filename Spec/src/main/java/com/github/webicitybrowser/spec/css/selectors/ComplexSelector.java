package com.github.webicitybrowser.spec.css.selectors;

public record ComplexSelector(ComplexSelectorPart[] parts, SelectorSpecificity specificity) implements ComplexSelectorPart {
	
}
