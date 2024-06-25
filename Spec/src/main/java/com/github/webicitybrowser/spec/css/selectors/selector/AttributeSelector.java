package com.github.webicitybrowser.spec.css.selectors.selector;

import com.github.webicitybrowser.spec.css.QualifiedName;

public interface AttributeSelector extends SimpleSelector {

	QualifiedName getAttributeName();
	
	AttributeSelectorOperation getOperation();
	
	String getComparisonValue();
	
	public static enum AttributeSelectorOperation {
		PRESENT, EQUALS, ONE_OF, HAS_SUBCODE, BEGINS_WITH, ENDS_WITH, CONTAINS
	}
	
}
