package everyos.browser.webicitybrowser.gui.component;

import com.github.anythingide.lace.imputils.ComponentBase;

public class TabButton extends ComponentBase {

	private String title;

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		if (this.title == null) {
			return "Untitled";
		}
		return this.title;
	}
	
}
