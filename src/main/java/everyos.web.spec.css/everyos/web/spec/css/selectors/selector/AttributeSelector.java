package everyos.web.spec.css.selectors.selector;

public interface AttributeSelector extends SimpleSelector {

	String getAttributeName();
	
	AttributeSelectorOperation getOperation();
	
	String getComparisonValue();
	
	public static enum AttributeSelectorOperation {
		EQUALS, NOT_EQUALS, HAS_SUBCODE, BEGINS_WITH, ENDS_WITH, CONTAINS
	}
	
}
