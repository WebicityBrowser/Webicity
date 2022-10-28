package everyos.web.spec.css.selectors.selector;

import everyos.web.spec.css.QualifiedName;

public interface AttributeSelector extends SimpleSelector {

	QualifiedName getAttributeName();
	
	AttributeSelectorOperation getOperation();
	
	String getComparisonValue();
	
	public static enum AttributeSelectorOperation {
		EQUALS, ONE_OF, HAS_SUBCODE, BEGINS_WITH, ENDS_WITH, CONTAINS
	}
	
}
