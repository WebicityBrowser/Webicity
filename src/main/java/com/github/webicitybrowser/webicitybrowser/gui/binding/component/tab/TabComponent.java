package com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab;

import com.github.webicitybrowser.thready.gui.tree.basics.imp.BaseComponent;

public abstract class TabComponent extends BaseComponent {
	
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
