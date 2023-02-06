package everyos.browser.webicitybrowser.gui.behavior;

import everyos.desktop.thready.basic.component.ComponentBase;
import everyos.desktop.thready.core.gui.InvalidationLevel;

public class URLBarComponent extends ComponentBase {
	
	private String value = "";
	
	public URLBarComponent setValue(String value) {
		this.value = value;
		invalidate(InvalidationLevel.PAINT);
		
		return this;
	}
	
	public String getValue() {
		return this.value;
	};
	
}
