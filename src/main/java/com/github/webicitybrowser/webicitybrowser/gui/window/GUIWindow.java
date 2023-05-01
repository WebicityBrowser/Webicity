package com.github.webicitybrowser.webicitybrowser.gui.window;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;

public interface GUIWindow {

	void addCloseListener(Runnable handler);

	void minimize();

	void restore();
	
	void close();

	void setPosition(AbsolutePosition newWindowPosition);

	AbsolutePosition getPosition();
	
}
