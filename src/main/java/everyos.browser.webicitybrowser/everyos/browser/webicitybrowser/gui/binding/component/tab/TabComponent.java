package everyos.browser.webicitybrowser.gui.binding.component.tab;

import everyos.desktop.thready.basic.component.ComponentBase;

public abstract class TabComponent extends ComponentBase {
	
	private boolean isTopLevel;
	
	public TabComponent() {}
	
	public abstract String getName();
	
	public abstract TabDisplayComponent getDisplay();

	public void setIsTopLevel(boolean isTopLevel) {
		this.isTopLevel = isTopLevel;
	}
	
	public boolean isTopLevel() {
		return this.isTopLevel;
	}

}
