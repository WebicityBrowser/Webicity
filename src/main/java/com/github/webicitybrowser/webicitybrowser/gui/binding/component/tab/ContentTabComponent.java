package com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab;

import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicitybrowser.ui.Tab;

public class ContentTabComponent extends TabComponent {

	private TabDisplayComponent display;

	public ContentTabComponent(TabSystemConfiguration configs, Tab tab) {
		this.display = configs.createTabDisplay(tab);
	}
	
	@Override
	public String getName() {
		return display.getName();
	}

	@Override
	public TabDisplayComponent getDisplay() {
		return this.display;
	}

	@Override
	public Class<? extends Component> getPrimaryType() {
		return TabComponent.class;
	}

}
