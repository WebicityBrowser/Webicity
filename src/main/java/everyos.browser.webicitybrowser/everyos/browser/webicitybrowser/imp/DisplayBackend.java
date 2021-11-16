package everyos.browser.webicitybrowser.imp;

public enum DisplayBackend {
	
	SKIJA("skija"), JLINE3("jline3");
	
	private final String name;

	DisplayBackend(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
}
