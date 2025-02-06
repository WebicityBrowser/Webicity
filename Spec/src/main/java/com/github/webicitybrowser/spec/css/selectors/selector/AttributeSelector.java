package com.github.webicitybrowser.spec.css.selectors.selector;

import com.github.webicitybrowser.spec.css.QualifiedName;

public record AttributeSelector(QualifiedName attributeName, AttributeSelectorOperation operation, String comparisonValue) implements SimpleSelector {
	
	public static enum AttributeSelectorOperation {
		PRESENT, EQUALS, ONE_OF, HAS_SUBCODE, BEGINS_WITH, ENDS_WITH, CONTAINS
	}
	
}
