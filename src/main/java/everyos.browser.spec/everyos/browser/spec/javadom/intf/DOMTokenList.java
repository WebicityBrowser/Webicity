package everyos.browser.spec.javadom.intf;

public interface DOMTokenList extends Iterable<String> {
	
	boolean contains(String token);
	int getLength();
	
}
