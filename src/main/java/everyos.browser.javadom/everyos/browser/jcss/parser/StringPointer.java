package everyos.browser.jcss.parser;

public class StringPointer {
	private String string;
	private int pointer;

	public StringPointer(String string, int pointer) {
		this.string = string;
		this.pointer = pointer;
	}
	
	public String getString() {
		return string;
	}
	
	public int getPointer() {
		return pointer;
	}
}
