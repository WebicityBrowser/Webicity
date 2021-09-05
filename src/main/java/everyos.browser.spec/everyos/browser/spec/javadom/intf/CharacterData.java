package everyos.browser.spec.javadom.intf;

public interface CharacterData extends Node {
	void appendData(String data);
	
	default void appendData(char data) {
		appendData(String.valueOf(data));
	}

	String getData();
}
