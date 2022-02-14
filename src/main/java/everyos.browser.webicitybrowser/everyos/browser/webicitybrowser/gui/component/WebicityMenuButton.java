package everyos.browser.webicitybrowser.gui.component;

import com.github.anythingide.lace.imputils.ComponentBase;

public class WebicityMenuButton extends ComponentBase {
	
	private String label;

	public WebicityMenuButton(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
}