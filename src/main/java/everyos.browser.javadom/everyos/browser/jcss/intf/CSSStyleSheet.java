package everyos.browser.jcss.intf;

public interface CSSStyleSheet extends StyleSheet {
	void setCSSRules(CSSRule[] parseAListOfRules);

	CSSRule[] getCSSRules();
}
