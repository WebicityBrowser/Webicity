package everyos.browser.webicity.renderer.html.dom;

public interface DOMTokenList {
	long getLength();
	String item(long index);
	boolean contains(String token);
	void add(String... tokens);
	void remove(String... tokens);
	boolean toggle(String token, boolean force);
	boolean replace(String token, String newToken);
	boolean supports(String token);
	String getValue();
	void setValue();
}
