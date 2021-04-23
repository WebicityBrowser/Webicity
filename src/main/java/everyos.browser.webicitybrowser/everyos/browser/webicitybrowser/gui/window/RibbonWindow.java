package everyos.browser.webicitybrowser.gui.window;

import everyos.engine.ribbon.core.component.Component;

public interface RibbonWindow {
	Component getDisplayPane();
	void close();
	void minimize();
	void restore();
}
