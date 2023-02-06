package everyos.browser.webicitybrowser.gui.binding.component.tab;

import everyos.browser.webicitybrowser.ui.Tab;

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

}
