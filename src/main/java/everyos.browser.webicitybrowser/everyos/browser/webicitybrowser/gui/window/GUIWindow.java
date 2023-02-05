package everyos.browser.webicitybrowser.gui.window;

import everyos.desktop.thready.core.positioning.AbsolutePosition;

public interface GUIWindow {

	void addCloseListener(Runnable handler);

	void minimize();

	void restore();
	
	void close();

	void setPosition(AbsolutePosition newWindowPosition);

	AbsolutePosition getPosition();
	
}
