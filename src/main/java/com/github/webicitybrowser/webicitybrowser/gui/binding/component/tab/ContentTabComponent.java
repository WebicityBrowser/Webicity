package com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab;

import java.util.function.Consumer;

import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicitybrowser.ui.Tab;

public class ContentTabComponent extends TabComponent {

	private final TabDisplayComponent display;
	private final Consumer<Component> onClose;

	public ContentTabComponent(TabSystemConfiguration configs, Tab tab, Consumer<Component> onClose) {
		this.display = configs.createTabDisplay(tab);
		this.onClose = onClose;
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

	@Override
	public void close() {
		onClose.accept(this);
	}

}
