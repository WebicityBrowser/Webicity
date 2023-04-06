package com.github.webicitybrowser.thready.windowing.core;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public interface DesktopWindow extends Window {

	void setVisible(boolean visible);
	
	AbsoluteSize getSize();
	
}
