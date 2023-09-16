package com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab;

import com.github.webicitybrowser.thready.gui.tree.basics.imp.BaseComponent;

public abstract class TabComponent extends BaseComponent {

	private boolean isTopLevel;
	
	public abstract String getName();
	
	public abstract TabDisplayComponent getDisplay();

	public abstract void close();

	public boolean isTopLevel() {
		return isTopLevel;
	}

	public void setIsTopLevel(boolean isTopLevel) {
		this.isTopLevel = isTopLevel;
	}

}
