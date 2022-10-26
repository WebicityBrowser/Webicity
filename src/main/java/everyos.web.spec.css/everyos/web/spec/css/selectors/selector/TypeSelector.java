package everyos.web.spec.css.selectors.selector;

public interface TypeSelector extends SimpleSelector {

	public static final String NO_NAMESPACE = "";
	public static final String ANY_NAMESPACE = "*";
	public static final String DEFAULT_NAMESPACE = "**";
	
	String getNamespace();
	
	SimpleSelector getInnerSelector();
	
}
